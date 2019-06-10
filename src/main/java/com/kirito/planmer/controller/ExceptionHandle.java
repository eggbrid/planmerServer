package com.kirito.planmer.controller;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.serviceImpl.BaseImpl;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseBean handle(Exception e) {
        if (e instanceof ErrorException) {
            ErrorException exception = (ErrorException) e;
            return BaseImpl.getErrorBean(exception.code,exception.message);
        }else {
            e.printStackTrace();
            return BaseImpl.getErrorBean("系统异常");
        }
    }
}
