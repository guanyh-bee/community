package com.gyh.community.dto;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;

import java.io.Serializable;

/**
 * @author gyh
 * @create 2020-07-30 14:04
 */
public class ResultDTO<T> implements Serializable {
    private Integer code;
    private String message;
    T data;

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static<T> ResultDTO errorOf(CustomizeErrorCode errorCode){
        return new ResultDTO<T>(errorCode.getCode(),errorCode.getMessage());
    }

    public static<T> ResultDTO errorOf(CustomizeException exception){
        return new ResultDTO<T>(exception.getCode(),exception.getMessage());
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
