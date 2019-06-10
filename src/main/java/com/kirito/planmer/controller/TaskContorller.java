package com.kirito.planmer.controller;

import com.kirito.planmer.bean.BaseBean;
import com.kirito.planmer.bean.TaskWithBLOBs;
import com.kirito.planmer.interfaces.UserLoginToken;
import com.kirito.planmer.service.TaskService;
import com.kirito.planmer.service.UserService;
import com.kirito.planmer.util.PLTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskContorller {

    @Resource
    private TaskService taskService;

    @RequestMapping("/test")
    @ResponseBody
    public BaseBean test(HttpServletRequest request) {
        TaskWithBLOBs taskWithBLOBs = new TaskWithBLOBs();
        taskWithBLOBs.setUserId(1);
        taskWithBLOBs.setTitle("测试任务");
        taskWithBLOBs.setdTime(30 * 1000l);
        taskWithBLOBs.setContent("我就是一个测试数据");
        taskWithBLOBs.setStarTime(System.currentTimeMillis());
        taskWithBLOBs.setEndTime(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000);
        return taskService.add(taskWithBLOBs);
    }
    @UserLoginToken
    @RequestMapping("/add")
    @ResponseBody
    public BaseBean add(HttpServletRequest request, @RequestParam("start_time") long start_time, @RequestParam("title") String title
            , @RequestParam("content") String content, @RequestParam("d_time") int d_time,@RequestParam("day") int day) {
        String userId= PLTokenUtil.getUserId(request.getHeader("token"));
        TaskWithBLOBs taskWithBLOBs = new TaskWithBLOBs();
        taskWithBLOBs.setUserId(Integer.parseInt(userId));
        taskWithBLOBs.setTitle(title);
        taskWithBLOBs.setdTime(d_time *60* 1000l);
        taskWithBLOBs.setContent(content);
        taskWithBLOBs.setStarTime(start_time);
        taskWithBLOBs.setEndTime(start_time + day * 24 * 60 * 60 * 1000);
        return taskService.add(taskWithBLOBs);
    }


    @UserLoginToken
    @RequestMapping("/home")
    @ResponseBody
    public BaseBean add(HttpServletRequest request) {
        String userId= PLTokenUtil.getUserId(request.getHeader("token"));
        return taskService.getHome(Integer.parseInt(userId));
    }


    @UserLoginToken
    @RequestMapping("/changeStatus")
    @ResponseBody
    public BaseBean changeStatus(HttpServletRequest  request,@RequestParam("status") int status,@RequestParam("task_info_id") int task_info_id) {
        String userId= PLTokenUtil.getUserId(request.getHeader("token"));
        return taskService.changeStatus(Integer.parseInt(userId),status,task_info_id);
    }

}
