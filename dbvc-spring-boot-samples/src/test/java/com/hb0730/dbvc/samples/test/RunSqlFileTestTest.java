package com.hb0730.dbvc.samples.test;

import com.hb0730.dbvc.core.RunSqlFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RunSqlFileTestTest {

    @Resource
    private RunSqlFile file;

    @Test
    public void test() throws IOException, SQLException {
        file.star();
    }
}
