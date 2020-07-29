package com.gyh.community.Exception;

/**
 * @author gyh
 * @create 2020-07-29 17:11
 */
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
