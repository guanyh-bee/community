package com.gyh.community.Exception;

/**
 * @author gyh
 * @create 2020-07-29 17:11
 */
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(ICustomizeErrorCode errorCode ){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CustomizeException() {
        super();
    }

    public CustomizeException(String message) {
        super(message);
    }

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomizeException(Throwable cause) {
        super(cause);
    }

    protected CustomizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
