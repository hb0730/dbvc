package com.hb0730.dbvc.spring;

import com.sun.istack.internal.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 读取sql文件
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class SqlFileUtils {

    /**
     * <p>
     * 获取文件夹下的sql文件
     * </p>
     *
     * @return file集
     */
    public static List<File> getFile() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:sql/*.sql");
        List<File> files = new ArrayList<>();
        for (Resource resource : resources) {
            files.add(resource.getFile());
        }
        return files;
    }

    /**
     * <p>
     * 读取内容
     * </p>
     *
     * @param file file文件
     * @return byte字节
     * @throws IOException
     */
    public static byte[] readFile(@NotNull File file) throws IOException {
        if (Objects.isNull(file)) {
            throw new NullPointerException("file is null");
        }
        return FileCopyUtils.copyToByteArray(file);
    }
}
