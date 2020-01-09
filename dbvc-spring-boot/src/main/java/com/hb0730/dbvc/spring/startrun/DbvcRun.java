package com.hb0730.dbvc.spring.startrun;

import com.hb0730.dbvc.core.RunSqlFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Component
public class DbvcRun implements ApplicationRunner {
    @Autowired
    private RunSqlFile runSqlFile;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runSqlFile.star();
    }
}
