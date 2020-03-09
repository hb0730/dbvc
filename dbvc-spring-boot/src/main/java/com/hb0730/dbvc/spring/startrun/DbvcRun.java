package com.hb0730.dbvc.spring.startrun;

import com.hb0730.dbvc.core.RunSqlFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 项目启动后自动执行start
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DbvcRun implements ApplicationListener<ContextRefreshedEvent> {
    private Log log = LogFactory.getLog(DbvcRun.class);
    @Value("${dbvc.enabled}")
    private boolean enabled;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (enabled) {
            log.debug("dbvc star>>>>>");
            if (event.getApplicationContext().getParent() == null) {
                RunSqlFile runSqlFile = event.getApplicationContext().getBean(RunSqlFile.class);
                runSqlFile.star();
                ;
            }
        }
    }
}
