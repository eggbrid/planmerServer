package com.kirito.planmer.serviceImpl;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.DataBean;

import java.util.HashMap;
import java.util.Map;

public class BaseImpl  {


    public static  <T> BaseBean<T> getSuccessBean(T t){
        return getBaseBean(200,"成功",t);
    }
    public static BaseBean getErrorBean(String message){
        return getBaseBean(400,message,null);
    }
    public static BaseBean getErrorBean(int code,String message){
        return getBaseBean(code,message,null);
    }
    public static <T> BaseBean<T> getBaseBean(int code,String messag,T t){
        Map<String,Object> datas=new HashMap<>();
        BaseBean<T> baseBean=new BaseBean<>();
        baseBean.setCode(code);
        baseBean.setMessage(messag);
        baseBean.setData(t);
        return baseBean;
    }
}
