package com.hb0730.dbvc.spring.autoconfigure;

import com.hb0730.dbvc.spring.core.RunSqlFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
public class DbvcAutoConfig {
    private Logger logger = LoggerFactory.getLogger(DbvcAutoConfig.class);

    @Autowired
    private DbvcProperties dbvcProperties;
    @PostConstruct
    public void check() {

    }

    @Bean
    @ConditionalOnMissingBean(RunSqlFile.class)
    public RunSqlFile runSqlFile() {
        return new RunSqlFile(dbvcProperties);
    }

}
