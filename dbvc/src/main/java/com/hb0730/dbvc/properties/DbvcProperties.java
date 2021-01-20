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
     * 需要读取SQL文件地址，默认为classpath:sql/*.sql
     */
    private String url = "classpath:sql/*.sql";

    /**
     * auto commit
     * 是否自动提交，默认为false
     */
    private boolean autoCommit = false;

    private boolean fullLineDelimiter = false;

    /**
     * delimiter
     * 定义命令间的分隔符
     */
    private String delimiter = ";";
    /**
     * 按照那种方式执行
     * <ul>
     *     <li>
     *         1. true则获取整个脚本并执行；
     *     </li>
     *     <li>
     *         2. false则按照自定义的分隔符每行执行；
     *     </li>
     * </ul>
     */
    private boolean sendFullScript = false;
    /**
     * 遇见错误是否停止；
     * <ul>
     *     <li>
     *         1. false，遇见错误不会停止，会继续执行，会打印异常信息，并不会抛出异常，当前方法无法捕捉异常无法进行回滚操作，无法保证在一个事务内执行；
     *     </li>
     *     <li>
     *         2. true，遇见错误会停止执行，打印并抛出异常，捕捉异常，并进行回滚，保证在一个事务内执行；
     *     </li>
     * </ul>
     */
    private boolean stopOnError = true;
    /**
     * 是否启用dbvc
     */
    private boolean enabled = true;
    /**
     * table name,内置的表名
     */
    private String tableName = "schema_history";

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
