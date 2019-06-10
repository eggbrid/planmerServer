package com.kirito.planmer.controller;

public class ErrorException extends Exception {
    public ErrorException(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int code;
    public String message;


}
