package com.yks.regit.common;

/**
 * 自定义野辅异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
