package com.kirito.planmer.controller;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.User;
import com.kirito.planmer.interfaces.UserLoginToken;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserContorller {
    @Resource
    private UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public BaseBean login(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("passWord") String password) {
        return userService.Login(userName, password);
    }

    @RequestMapping("/register")
    @ResponseBody
    public BaseBean register(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("passWord") String password,@RequestParam("invitationCode") String invitationCode) {
        return userService.regiester(userName, password,invitationCode);
    }
    @RequestMapping("/test")
    @ResponseBody
    public BaseBean login(HttpServletRequest request) {
        return userService.regiester("ceshi", "123456","L2Q2");
    }

}
