package com.kirito.planmer.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.User;
import com.kirito.planmer.controller.ErrorException;
import com.kirito.planmer.interfaces.PassToken;
import com.kirito.planmer.interfaces.UserLoginToken;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.util.Constant;
import com.kirito.planmer.util.PLTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");
                }
                String userName=PLTokenUtil.getUsername(token);
                String userId=PLTokenUtil.getUserId(token);
                if (StringUtils.isEmpty(userId)){
                    throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");
                }

                if (StringUtils.isEmpty(userName)){
                    throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");
                }
                BaseBean<User> user = userService.serviceGetUser(userName);

                if (user == null) {
                    throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");
                }


                try {
                   boolean b= PLTokenUtil.verify(token,user.getData().getUserName(),user.getData().getPassWord());
                    if (!b){
                        throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");

                    }
                } catch (JWTVerificationException e) {
                    throw new ErrorException(Constant.NOTLOGIN_CODE,"无效token，请重新登录");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}