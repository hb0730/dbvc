package com.hb0730.dbvc.samples.test;

import com.hb0730.dbvc.spring.core.SqlFileUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class SqlFileUtilsTest {

    @Test
    public void getFileTest() {
        SqlFileUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "sql/*.sql");
    }
}
