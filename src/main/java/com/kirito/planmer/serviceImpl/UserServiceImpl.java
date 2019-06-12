package com.kirito.planmer.serviceImpl;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.User;
import com.kirito.planmer.bean.UserInfo;
import com.kirito.planmer.mapper.UserInfoMapper;
import com.kirito.planmer.mapper.UserMapper;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.util.PLTokenUtil;
import com.kirito.planmer.util.ShareCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends BaseImpl implements UserService {
    @Resource
    public UserMapper userMapper;
    @Resource
    public UserInfoMapper userInfoMapper;
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

    @Override
    public BaseBean<User> regiester(String userName, String password, String invitationCode) {

        if (StringUtils.isEmpty(userName)){
            return getErrorBean("用户名不能为空");

        }
        if (StringUtils.isEmpty(password)){
            return getErrorBean("密码不能为空");

        }
        if (StringUtils.isEmpty(invitationCode)){
            return getErrorBean("邀请码不能为空");

        }
        User user=userMapper.login(userName);
        if (user!=null){
            return getErrorBean("该账户已被注册了，建议您用手机号注册");
        }else{
             user=new User();
            user.setPassWord(password);
            user.setUserName(userName);
            if ("000000".equals(invitationCode)){
                user.setIvUserId(50000);
            }else{
                int icId=ShareCodeUtil.codeToId(invitationCode);
                User icUser=userMapper.getUserById(icId);
                if (icUser==null){
                    return getErrorBean("邀请码不正确呢");

                }
                user.setIvUserId(icUser.getId());
            }
            userMapper.insert(user);
            String token= PLTokenUtil.sign(userName,password,user.getId()+"");
            user.setToken(token);
            user.setInvitationCode(ShareCodeUtil.idToCode(user.getId()));
            userMapper.update(user);
            UserInfo userInfo=new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setNickName("计划猫用户"+user.getId());
            userInfoMapper.insert(userInfo);
            return getSuccessBean(user);
        }


    }
}
