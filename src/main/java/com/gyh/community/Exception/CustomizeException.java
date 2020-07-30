package com.gyh.community.Exception;

/**
 * @author gyh
 * @create 2020-07-29 17:11
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private  Integer code;
    public CustomizeException(ICustomizeErrorCode errorCode ){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
