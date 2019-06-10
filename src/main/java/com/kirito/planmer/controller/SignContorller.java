package com.kirito.planmer.controller;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.interfaces.UserLoginToken;
import com.kirito.planmer.service.SignService;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.util.PLTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sign")
public class SignContorller {
    @Resource
    private SignService signService;

    @UserLoginToken

    @RequestMapping("/add")
    @ResponseBody
    public BaseBean login(HttpServletRequest request) {
        String userId= PLTokenUtil.getUserId(request.getHeader("token"));
        return signService.sign(Integer.parseInt(userId));
    }
}
