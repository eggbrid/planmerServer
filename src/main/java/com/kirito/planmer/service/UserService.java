package com.kirito.planmer.service;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.User;

public interface UserService {

    public BaseBean<User> Login(String userName, String password);
    public BaseBean<User> serviceGetUser(String userName);
    public BaseBean<User> regiester(String userName, String password,String invitationCode);


}
