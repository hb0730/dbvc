package com.hb0730.dbvc.core;

import com.hb0730.dbvc.exception.DbvcException;
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
 * @since V1.1.1
 */
class SqlFileUtils {
    private static final int NOT_FOUND = -1;
    /**
     * <p>
     * The extension separator character.
     * </p>
     *
     * @since v1.1.1
     */
    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * <p>
     * The extension separator String.
     * </p>
     *
     * @since v1.1.1
     */
    private static final String EXTENSION_SEPARATOR_STR = Character.toString(EXTENSION_SEPARATOR);

    /**
     * The Unix separator character.
     *
     * @since v1.1.1
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     *
     * @since v1.1.1
     */
    private static final char WINDOWS_SEPARATOR = '\\';

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
                    String fileName = removeExtension(filename);
                    if (fileName.length() < 3) {
                        throw new DbvcException("file name length Min 3");
                    }
                    String extension = getExtension(filename);
                    File file = File.createTempFile(fileName, extension);
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

    /**
     * <p>
     * Removes the extension from a filename.
     * <pre>
     *  foo.txt    --&gt; foo
     * </pre>
     * </p>
     *
     * @param filename file name
     * @return the filename minus the extension
     */
    private static String removeExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        final int index = indexOfExtension(filename);
        if (index == NOT_FOUND) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    /**
     * <p>
     * get filename Extension
     * <pre>
     * foo.txt      --&gt; ".txt"
     * </pre>
     * </p>
     *
     * @param filename file name
     * @return extension
     * @since v1.1.1
     */
    private static String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == NOT_FOUND) {
            return "";
        } else {
            return filename.substring(index);
        }
    }

    /**
     * <p>
     * find last .
     * </P>
     *
     * @param filename file name
     * @return index
     * @since v1.1.1
     */
    private static int indexOfExtension(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        final int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
    }

    /**
     * <p>
     * find last .
     * </p>
     *
     * @param filename file name
     * @return index
     * @since v1.1.1
     */
    private static int indexOfLastSeparator(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }
}
