package com.hb0730.dbvc.core;

import com.hb0730.dbvc.exception.DbvcException;
import com.hb0730.dbvc.properties.DbvcProperties;
import com.hb0730.dbvc.properties.FileInputStreamProperties;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
    private Logger logger = LoggerFactory.getLogger(RunSqlFile.class);
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
        Map<String, FileInputStreamProperties> collect = file.stream().collect(Collectors.toMap(FileInputStreamProperties::getFileName, account -> account));
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
    private void run(FileInputStreamProperties file) {
        if (Objects.isNull(file)) {
            return;
        }
        try {
            ScriptRunner runner = new ScriptRunner(getConnection());
            runner.setAutoCommit(properties.isAutoCommit());
            runner.setFullLineDelimiter(properties.isFullLineDelimiter());
            runner.setDelimiter(properties.getDelimiter());
            runner.setSendFullScript(properties.isSendFullScript());
            runner.setStopOnError(properties.isStopOnError());
            if (!logger.isDebugEnabled()) {
                runner.setLogWriter(null);
            }
            runner.runScript(new InputStreamReader(file.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run mybatis ScriptRunner error,Message:{}", e.getMessage());
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
        if (!CollectionUtils.isEmpty(files)){
            createTabled();
            for (FileInputStreamProperties file : files) {
                long start = System.currentTimeMillis();
                int success = 1;
                String fileName = "";
                try {
                    fileName = file.getFileName();
                    run(file);
                }catch (Exception e) {
                    success = 0;
                    throw new DbvcException(e.getMessage());
                }finally {
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
        logger.debug("create table>>>>>>>");

        String selectSql = String.format("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA=(select database()) AND table_name = '%s'", properties.getTableName());
        String createSql = String.format("create table %s( `id` int(11) NOT NULL AUTO_INCREMENT,`description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,`createTime` datetime(0) NULL DEFAULT NULL,`success` int(2) NULL DEFAULT NULL, `execution_time` int(11) NULL DEFAULT NULL, PRIMARY KEY (`id`) USING BTREE)", properties.getTableName());
        try {
            ResultSet resultSet = getConnection().prepareStatement(selectSql).executeQuery();
            if (!resultSet.next()) {
                getConnection().createStatement().executeUpdate(createSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("create table error,{}", e.getMessage());
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
        logger.debug("insert db version controller");
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
            logger.error("insert dbvc error, message:{}", e.getMessage());
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
            logger.error("select error,{}", e.getMessage());
            throw new DbvcException("select error");
        }
    }
}
