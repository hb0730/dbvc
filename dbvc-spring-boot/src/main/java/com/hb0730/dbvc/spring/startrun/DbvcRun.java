package com.hb0730.dbvc.spring.startrun;

import com.hb0730.dbvc.core.RunSqlFile;
import com.hb0730.dbvc.exception.DbvcException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DbvcRun implements ApplicationListener<ContextRefreshedEvent> {
    private Log log = LogFactory.getLog(DbvcRun.class);
    @Autowired
    private RunSqlFile runSqlFile;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                runSqlFile.star();
            } catch (SQLException e) {
                log.debug("start dbvc error:{}", e);
                e.printStackTrace();
                throw new DbvcException("star dbvc error, message:" + e.getMessage());
            }
            ;
        }
    }
}