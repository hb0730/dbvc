package com.hb0730.dbvc.properties;

import java.util.Objects;

/**
 * <p>
 * 配置
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class DbvcProperties {

    /**
     * sql Script url
     */
    private String url = "classpath:sql/*.sql";

    /**
     * auto commit
     */
    private boolean autoCommit = true;

    private boolean fullLineDelimiter = false;

    /**
     * delimiter
     */
    private String delimiter = ";";

    private boolean sendFullScript = false;

    private boolean stopOnError = true;

    private boolean enabled = true;
    /**
     * table name
     */
    private String tableName= "schema_history";

    public DbvcProperties(String url, boolean autoCommit, boolean fullLineDelimiter, String delimiter, boolean sendFullScript, boolean stopOnError, boolean enabled, String tableName) {
        this.url = url;
        this.autoCommit = autoCommit;
        this.fullLineDelimiter = fullLineDelimiter;
        this.delimiter = delimiter;
        this.sendFullScript = sendFullScript;
        this.stopOnError = stopOnError;
        this.enabled = enabled;
        this.tableName = tableName;
    }

    public DbvcProperties() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public boolean isFullLineDelimiter() {
        return fullLineDelimiter;
    }

    public void setFullLineDelimiter(boolean fullLineDelimiter) {
        this.fullLineDelimiter = fullLineDelimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public boolean isSendFullScript() {
        return sendFullScript;
    }

    public void setSendFullScript(boolean sendFullScript) {
        this.sendFullScript = sendFullScript;
    }

    public boolean isStopOnError() {
        return stopOnError;
    }

    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbvcProperties that = (DbvcProperties) o;
        return autoCommit == that.autoCommit &&
                fullLineDelimiter == that.fullLineDelimiter &&
                sendFullScript == that.sendFullScript &&
                stopOnError == that.stopOnError &&
                enabled == that.enabled &&
                Objects.equals(url, that.url) &&
                Objects.equals(delimiter, that.delimiter) &&
                Objects.equals(tableName, that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, autoCommit, fullLineDelimiter, delimiter, sendFullScript, stopOnError, enabled, tableName);
    }

    @Override
    public String toString() {
        return "DbvcProperties{" +
                "url='" + url + '\'' +
                ", autoCommit=" + autoCommit +
                ", fullLineDelimiter=" + fullLineDelimiter +
                ", delimiter='" + delimiter + '\'' +
                ", sendFullScript=" + sendFullScript +
                ", stopOnError=" + stopOnError +
                ", enabled=" + enabled +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
