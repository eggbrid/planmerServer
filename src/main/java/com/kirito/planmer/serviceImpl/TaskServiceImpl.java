package com.kirito.planmer.serviceImpl;

import com.kirito.planmer.bean.*;
import com.kirito.planmer.mapper.*;
import com.kirito.planmer.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl extends BaseImpl implements TaskService {
    @Resource
    public TaskMapper taskMapper;
    @Resource
    public SignMapper signMapper;
    @Resource
    public SignInfoMapper signInfoMapper;
    @Resource
    public TaskInfoMapper taskInfoMapper;

    SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat simpleFormatterDay = new SimpleDateFormat("yyyy-MM-dd");
    public long aDay = 24 * 60 * 60 * 1000;

    @Override
    public BaseBean add(TaskWithBLOBs task) {
        taskMapper.insert(task);
        return getSuccessBean(null);
    }

    @Override
    public BaseBean<Map<String, Object>> getHome(int user_id) {
        Map<String, Object> map = new HashMap<>();
        List<TaskWithBLOBs> taskWithBLOBs = taskMapper.getTaskByUserId(user_id, System.currentTimeMillis());

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
        TaskWithBLOBs taskWithBLOB = null;
        if (taskWithBLOBs != null && taskWithBLOBs.size() > 0) {
            taskWithBLOB = taskWithBLOBs.get(0);

            TaskInfo taskInfo = taskInfoMapper.getTaskByToday(user_id, startTime.getTime(), endTime.getTime());
            if (taskInfo != null) {
                //如果一开始，并且时间已经超过持续时间，就帮忙设置为已经完成
                if (taskInfo.getStatus() == 1 && (date.getTime() - taskInfo.getStartTime()) >= taskWithBLOB.getdTime()) {
                    taskInfo.setEndTime(taskInfo.getStartTime() + taskWithBLOB.getdTime());
                    taskInfo.setStatus(3);
                }else if (taskInfo.getStatus()==1){
                    taskInfo.setEndTime(date.getTime());

                }
                taskInfoMapper.updateTaskInfo(taskInfo);

            } else {
                taskInfo = new TaskInfo();
                taskInfo.setStatus(0);
                taskInfo.setStartTime(startTime.getTime());
                taskInfo.setEndTime(startTime.getTime());
                taskInfo.setUserId(user_id);
                taskInfo.setTaskId(taskWithBLOB.getId());
                taskInfoMapper.insert(taskInfo);
                taskInfo = taskInfoMapper.getTaskByToday(user_id, startTime.getTime(), endTime.getTime());

            }
            map.put("taskInfo", taskInfo);

        }
        map.put("task", taskWithBLOB);




        SignInfo todaySignInfo = signInfoMapper.getSignInfoByUser(user_id, startTime.getTime(), endTime.getTime());

        Sign sign = signMapper.getUserSign(user_id);
        if (sign == null) {
            sign = new Sign();
            sign.setDays(0);
            sign.setUserId(user_id);
            signMapper.insert(sign);
            sign = signMapper.getUserSign(user_id);
        }else{
            if (todaySignInfo==null){
                SignInfo yesSignInfo = signInfoMapper.getSignInfoByUser(user_id, startTime.getTime()-aDay, endTime.getTime()-aDay);
                sign.setDays(yesSignInfo==null?0:sign.getDays());
                signMapper.updateUserSign(sign);
            }
        }



        map.put("sign", sign);
        map.put("isTodaySign", todaySignInfo == null ? 0 : 1);

        return getSuccessBean(map);
    }

    @Override
    public BaseBean<TaskInfo> changeStatus(int user_id, int status, int task_info_id) {
        Date date = new Date();
        TaskInfo taskInfo = taskInfoMapper.getTaskById(task_info_id);

        if (taskInfo != null) {
            if (status<0||status>4){
                return getErrorBean("操作好像有误呢，刷新下试试");

            }
            if (status == 1) {
                if (taskInfo.getStatus() == 0 || taskInfo.getStatus() == 2) {
                    if (taskInfo.getStatus() == 0) {
                        taskInfo.setStartTime(date.getTime());
                        taskInfo.setEndTime(date.getTime());
                    }

                    if (taskInfo.getStatus() == 2) {
                        taskInfo.setStartTime(date.getTime()-(taskInfo.getEndTime()-taskInfo.getStartTime()));
                        taskInfo.setEndTime(date.getTime());
                    }
                    taskInfo.setStatus(1);
                    taskInfoMapper.updateTaskInfo(taskInfo);
                } else {
                    return getErrorBean("操作好像有误呢，刷新下试试");
                }
            } else if (status == 2) {
                if (taskInfo.getStatus() == 1) {
                    taskInfo.setEndTime(date.getTime());
                    taskInfo.setStatus(2);
                    taskInfoMapper.updateTaskInfo(taskInfo);
                }else{
                    return getErrorBean("操作好像有误呢，刷新下试试");

                }
            }else{
                taskInfo.setEndTime(date.getTime());
                taskInfo.setStatus(status);
                taskInfoMapper.updateTaskInfo(taskInfo);
            }

        } else {

            return getErrorBean("计划已经不存在啦～");


        }
        TaskInfo success = taskInfoMapper.getTaskById(task_info_id);

        return getSuccessBean(success);
    }
}
