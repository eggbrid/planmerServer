package com.kirito.planmer.serviceImpl;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.User;
import com.kirito.planmer.mapper.UserMapper;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.util.PLTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends BaseImpl implements UserService {
    @Resource
    public UserMapper userMapper;

    @Override
    public BaseBean Login(String userName, String password) {
        User user=userMapper.login(userName);
        if (user==null){
            return getErrorBean("用户名错误");

        }else {
            if (user.getPassWord().equals(password)){

                String token= PLTokenUtil.sign(userName,password,user.getId()+"");
                user.setToken(token);
                return getSuccessBean(user);
            }else {
                return getErrorBean("密码错误");
            }
        }
    }

    @Override
    public BaseBean<User> serviceGetUser(String userName) {
        User user=userMapper.login(userName);

        return getSuccessBean(user);
    }
}
