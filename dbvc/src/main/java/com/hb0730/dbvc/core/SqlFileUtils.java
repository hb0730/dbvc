package com.hb0730.dbvc.core;

import com.hb0730.dbvc.properties.FileInputStreamProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * <p>
 * 读取.sql文件
 * </P>
 *
 * @author bing_huang
 * @since V1.1.1
 */
class SqlFileUtils {

    /**
     * <p>
     * 获取文件夹下的sql文件
     * </p>
     *
     * @param url 地址
     * @return file集
     */
    static List<FileInputStreamProperties> getFile(String url) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = resolver.getResources(url);
            List<FileInputStreamProperties> streams = new ArrayList<>();
            List<Resource> resourceList = Arrays.asList(resources);
            if (!CollectionUtils.isEmpty(resourceList)) {
                resourceList.forEach((resource) -> addFileInput(resource, streams));
            }
            return streams;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void addFileInput(Resource resource, List<FileInputStreamProperties> streams) {
        if (!Objects.isNull(resource)) {
            try {
                InputStream inputStream = resource.getInputStream();
                String filename = resource.getFilename();
                if (filename != null) {
                    BiFunction<String, InputStream, FileInputStreamProperties> function = FileInputStreamProperties::new;
                    streams.add(function.apply(filename, inputStream));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
