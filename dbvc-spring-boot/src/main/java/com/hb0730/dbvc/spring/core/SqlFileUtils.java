package com.hb0730.dbvc.spring.core;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
     * @param url 地址
     * @return file集
     */
    public static List<File> getFile(String url) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = resolver.getResources(url);
            List<File> files = new ArrayList<>();
            for (Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                String filename = resource.getFilename();
                if (filename != null) {
                    File file = new File(filename);
                    byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                    FileCopyUtils.copy(bytes, file);
                    files.add(file);
                }
            }
            return files;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
