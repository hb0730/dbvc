package com.hb0730.dbvc.spring.autoconfigure;

import com.hb0730.dbvc.core.RunSqlFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
@EnableConfigurationProperties(DbvcProperties.class)
@ConditionalOnProperty(prefix = "dbvc", value = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RunSqlFile.class)
@ComponentScan("com.hb0730.dbvc")
public class DbvcAutoConfig {
    private Logger logger = LoggerFactory.getLogger(DbvcAutoConfig.class);

    @Autowired
    private DbvcProperties dbvcProperties;
    @Resource
    private DataSource dataSource;

    @PostConstruct
    public void check() {

    }

    @Bean
    @ConditionalOnMissingBean(RunSqlFile.class)
    public RunSqlFile runSqlFile() throws SQLException {
        Connection connection = dataSource.getConnection();
        return new RunSqlFile(dbvcProperties, connection);
    }

}
