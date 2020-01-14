package com.hb0730.dbvc.core;

import com.hb0730.dbvc.exception.DbvcException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 读取.sql文件
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
class SqlFileUtils {
    private static final String SQL=".sql";
    /**
     * <p>
     * 获取文件夹下的sql文件
     * </p>
     *
     * @param url 地址
     * @return file集
     */
    static List<File> getFile(String url) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = resolver.getResources(url);
            List<File> files = new ArrayList<>();
            for (Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                String filename = resource.getFilename();
                if (filename != null) {
                    filename = filename.substring(0, filename.lastIndexOf("."));
                    if (filename.length() < 3) {
                        throw new DbvcException("file name length Min 3");
                    }
                    File file = File.createTempFile(filename, SQL);
                    byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                    FileCopyUtils.copy(bytes, file);
                    files.add(file);
                }
            }
            return files;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
