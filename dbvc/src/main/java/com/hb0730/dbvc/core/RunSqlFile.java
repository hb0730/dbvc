package com.hb0730.dbvc.core;

import com.hb0730.dbvc.exception.DbvcException;
import com.hb0730.dbvc.properties.DbvcProperties;
import com.hb0730.dbvc.properties.FileInputStreamProperties;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 执行sqlFile
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class RunSqlFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunSqlFile.class);
    private DbvcProperties properties;
    private Connection connection;

    public RunSqlFile(DbvcProperties properties, Connection connection) {
        this.properties = properties;
        this.connection = connection;
    }

    public RunSqlFile() {
    }

    /**
     * <p>
     * 获取链接
     * </p>
     *
     * @return 连接
     */
    private Connection getConnection() {
        return connection;
    }


    /**
     * <p>
     * 获取脚本文件
     * </p>
     *
     * @return 脚本文件
     */
    private List<FileInputStreamProperties> readFile() {
        List<FileInputStreamProperties> file = SqlFileUtils.getFile(properties.getUrl());
        if (CollectionUtils.isEmpty(file)) {
            return null;
        }
        // 保证有序
        Map<String, FileInputStreamProperties> collect = file
                .stream()
                .collect(Collectors.toMap(FileInputStreamProperties::getFileName, Function.identity(), (v1, v2) -> v2, LinkedHashMap::new));
        Set<String> strings = collect.keySet();
        createTabled();
        List<String> all = getAll();
        strings.removeAll(all);
        List<FileInputStreamProperties> newFile = new ArrayList<>();
        strings.forEach((s) -> newFile.add(collect.get(s)));
        return newFile;
    }

    /**
     * <p>
     * 运行
     * </p>
     *
     * @param file sql文件集
     */
    private void run(FileInputStreamProperties file) throws SQLException {
        if (Objects.isNull(file)) {
            return;
        }
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        boolean autoCommit = false;
        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(getConnection());
            // 不自动提交
            runner.setAutoCommit(properties.isAutoCommit());
            // 定义命令间的分隔符
            runner.setDelimiter(properties.getDelimiter());
            runner.setFullLineDelimiter(properties.isFullLineDelimiter());
            // true则获取整个脚本并执行；
            // false则按照自定义的分隔符每行执行；
            runner.setSendFullScript(properties.isSendFullScript());
            runner.setStopOnError(properties.isStopOnError());
            //   // 设置是否输出日志，null不输出日志，不设置自动将日志输出到控制台
            if (!LOGGER.isDebugEnabled()) {
                runner.setLogWriter(null);
            }
            runner.runScript(new InputStreamReader(file.getInputStream()));
            connection.commit();
            //重新设置 保持原有
            connection.setAutoCommit(autoCommit);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("run mybatis ScriptRunner error,Message:{}", e.getMessage());
            // rollback
            connection.rollback();
            connection.setAutoCommit(autoCommit);
            throw new DbvcException("run mybatis ScriptRunner error");
        }


    }

    /**
     * <p>
     * 启动
     * </p>
     */
    public void star() {
        List<FileInputStreamProperties> files = readFile();
        if (!CollectionUtils.isEmpty(files)) {
            createTabled();
            for (FileInputStreamProperties file : files) {
                long start = System.currentTimeMillis();
                int success = 1;
                String fileName = "";
                try {
                    fileName = file.getFileName();
                    run(file);
                } catch (Exception e) {
                    success = 0;
                    throw new DbvcException(e.getMessage());
                } finally {
                    long end = System.currentTimeMillis();
                    java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
                    insert(fileName, date, success, end - start);
                }
            }
        }
    }

    /**
     * 是否创建表结构
     */
    private void createTabled() {
        LOGGER.debug("create table>>>>>>>");

        String selectSql = String.format("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA=(select database()) AND table_name = '%s'", properties.getTableName());
        String createSql = String.format("create table %s( `id` int(11) NOT NULL AUTO_INCREMENT,`description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,`createTime` datetime(0) NULL DEFAULT NULL,`success` int(2) NULL DEFAULT NULL, `execution_time` int(11) NULL DEFAULT NULL, PRIMARY KEY (`id`) USING BTREE)", properties.getTableName());
        try {
            ResultSet resultSet = getConnection().prepareStatement(selectSql).executeQuery();
            if (!resultSet.next()) {
                getConnection().createStatement().executeUpdate(createSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("create table error,{}", e.getMessage());
            throw new DbvcException("create table error");
        }
    }

    /**
     * <p>
     * 新增记录
     * </p>
     *
     * @param description   filename
     * @param date          创建时间
     * @param success       是否成功
     * @param executionTime 耗时
     */
    private void insert(String description, java.sql.Date date, int success, long executionTime) {
        LOGGER.debug("insert db version controller");
        Connection connection = getConnection();
        String sql = String.format("insert into %s(`description`,`createTime`,`success`,`execution_time`)Values(?,?,?,?)", properties.getTableName());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, success);
            preparedStatement.setLong(4, executionTime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("insert dbvc error, message:{}", e.getMessage());
            throw new DbvcException("insert dbvc error");
        }
    }

    /**
     * <p>
     * 获取记录
     * </p>
     *
     * @return filename
     */
    private List<String> getAll() {
        String sql = String.format("select description from %s WHERE success=1", properties.getTableName());
        try {
            ResultSet resultSet = getConnection().prepareStatement(sql).executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("select error,{}", e.getMessage());
            throw new DbvcException("select error");
        }
    }
}
