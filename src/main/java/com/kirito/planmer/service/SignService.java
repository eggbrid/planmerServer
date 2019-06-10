package com.kirito.planmer.service;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.Sign;

import java.text.ParseException;

public interface SignService {
    /**
     * 通过userid获取用户签到天数
     * @param userId
     * @return
     */
    public BaseBean<Sign> sign(int userId) ;
}
