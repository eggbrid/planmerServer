package com.kirito.planmer.serviceImpl;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.Sign;
import com.kirito.planmer.bean.SignInfo;
import com.kirito.planmer.mapper.SignInfoMapper;
import com.kirito.planmer.mapper.SignMapper;
import com.kirito.planmer.service.SignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;
@Service
public class SignServiceImpl extends BaseImpl implements SignService {
    @Resource
    public SignMapper signMapper;

    public long aDay = 24 * 60 * 60 * 1000;
    @Resource
    public SignInfoMapper signInfoMapper;
    SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat simpleFormatterDay = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public BaseBean<Sign> sign(int userId) {
        Date date = new Date();

        Date startTime = null;
        try {
            startTime = simpleFormatter.parse(simpleFormatterDay.format(date) + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return getErrorBean("日期解析失败");

        }
        Date endTime = null;
        try {
            endTime = simpleFormatter.parse(simpleFormatterDay.format(date) + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return getErrorBean("日期解析失败");
        }
        SignInfo todaySignInfo=signInfoMapper.getSignInfoByUser(userId, startTime.getTime(), endTime.getTime() );
        if (todaySignInfo!=null){
            return getErrorBean("今天已经签到了");
        }

        SignInfo beSignInfo=signInfoMapper.getSignInfoByUser(userId, startTime.getTime() - aDay, endTime.getTime() - aDay);


        int status=beSignInfo==null?0:1;
        SignInfo signInfo = new SignInfo();
        signInfo.setStatus(status);
        signInfo.setTime(date.getTime());
        signInfo.setUserId(userId);
        signInfoMapper.insert(signInfo);
        Sign sign = signMapper.getUserSign(userId);
        if (sign == null) {
            sign = new Sign();
            sign.setDays(1);
            sign.setUserId(userId);
            signMapper.insert(sign);
            sign = signMapper.getUserSign(userId);
        } else {
            sign.setDays(status==0?1:sign.getDays() + 1);
            signMapper.updateUserSign(sign);
        }


        return getSuccessBean(sign);
    }
}
