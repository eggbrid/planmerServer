package com.kirito.planmer.bean;

import java.io.Serializable;

public class DataBean<T> implements Serializable {
    public DataBean(T t){
     this.data=t;
    }
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
