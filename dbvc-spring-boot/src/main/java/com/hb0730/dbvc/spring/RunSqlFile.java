package com.hb0730.dbvc.spring;

import com.hb0730.dbvc.exption.DbvcException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class RunSqlFile {
    private Logger logger = LoggerFactory.getLogger(RunSqlFile.class);
    @Resource
    public SqlSessionFactory factory;

    /**
     * <p>
     * 获取链接
     * </p>
     *
     * @return 连接
     */
    private Connection getConnection() {
        return factory.openSession().getConnection();
    }


    /**
     * <p>
     * 获取脚本文件
     * </p>
     *
     * @return 脚本文件
     * @throws IOException
     */
    private List<File> readFile() throws IOException {
        List<File> file = SqlFileUtils.getFile();
        Map<String, File> collect = file.stream().collect(Collectors.toMap(File::getName, account -> account));
        Set<String> strings = collect.keySet();
        createTabled();
        List<String> all = getAll();
        strings.removeAll(all);
        List<File> newFile = new ArrayList<>();
        strings.forEach((s) -> newFile.add(collect.get(s)));
        return newFile;
    }

    /**
     * <p>
     * 运行
     * </p>
     *
     * @param files
     * @throws FileNotFoundException
     */
    private void run(List<File> files) throws SQLException {
        try {
            ScriptRunner runner = new ScriptRunner(getConnection());
            //自动提交
            runner.setAutoCommit(true);
            runner.setFullLineDelimiter(false);
            //分隔符
            runner.setDelimiter(";");
            runner.setSendFullScript(false);
            runner.setStopOnError(true);
            //runner.setLogWriter(null);
            for (File file : files) {
                runner.runScript(new InputStreamReader(new FileInputStream(file)));
            }
            runner.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("run mybatis ScriptRunner error,Message:{}", e.getMessage());
            logger.error("run mybatis ScriptRunner error");
        }


    }

    public void star() throws IOException, SQLException {
        List<File> files = readFile();
        if (!CollectionUtils.isEmpty(files)) {
            createTabled();
            run(files);
        }
    }

    /**
     * 是否创建表结构
     */
    private void createTabled() {
        logger.debug("create table>>>>>>>");
        String selectSql = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA=(select database()) AND table_name = 'schema_history'";
        String createSql = "create table schema_history( `id` int(11) NOT NULL AUTO_INCREMENT,`description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL," +
                "`createTime` datetime(0) NULL DEFAULT NULL,`success` int(2) NULL DEFAULT NULL, `execution_time` int(11) NULL DEFAULT NULL," +
                " PRIMARY KEY (`id`) USING BTREE) ";
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
     * @return 是否成功
     */
    private int insert(String description, Date date, int success, int executionTime) {
        logger.debug("insert db version controller");
        int insert = 0;
        SqlSession sqlSession = factory.openSession();
        String builder = "insert into schema_history" +
                "(" +
                "description,createTime,success,execution_time" +
                ")" +
                "VALUES(" +
                description +
                date +
                success +
                executionTime +
                ")";
        insert = sqlSession.insert(builder);
        sqlSession.commit();
        return insert;
    }

    /**
     * <p>
     * 获取记录
     * </p>
     *
     * @return filename
     */
    private List<String> getAll() {
        String sql = "select description from schema_history";
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
