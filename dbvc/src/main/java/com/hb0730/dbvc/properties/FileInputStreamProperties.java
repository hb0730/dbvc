package com.hb0730.dbvc.properties;

import java.io.InputStream;
import java.util.Objects;

/**
 * <p>
 *
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class FileInputStreamProperties {
    private String fileName;
    private InputStream inputStream;

    public FileInputStreamProperties(String fileName, InputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public FileInputStreamProperties() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInputStreamProperties that = (FileInputStreamProperties) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(inputStream, that.inputStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, inputStream);
    }

    @Override
    public String toString() {
        return "FileInputStreamProperties{" +
                "fileName='" + fileName + '\'' +
                ", inputStreamReader=" + inputStream +
                '}';
    }
}
