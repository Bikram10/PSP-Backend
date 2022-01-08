package com.bikram.appliedproject.service.exception;

public class BadOrderException extends RuntimeException{

    public BadOrderException(String msg){
        super(msg);
    }
}
