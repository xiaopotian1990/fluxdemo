package com.example.exceptions;

/**
 * ==========================================
 * Created with IntelliJ IDEA.
 * User: 小破天
 * Date: 2018-05-30
 * Time: 0:44
 * 博客园：http://www.cnblogs.com/xiaopotian/
 * ===========================================
 */
public class CheckException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 出错字段的名字
     */
    private String fieldName;
    /**
     * 出错字段的值
     */
    private String fieldValue;

    public CheckException() {
        super();
    }

    public CheckException(String message, Throwable cause,
                          boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
