package com.kirito.planmer.service;

import com.kirito.planmer.bean.*;
import org.apache.tomcat.util.modeler.BaseModelMBean;

import java.util.Map;

public interface TaskService {

    public BaseBean add(TaskWithBLOBs task);

    public BaseBean<Map<String,Object>> getHome(int user_id);

    public BaseBean<TaskInfo> changeStatus(int user_id,int status,int task_info_id);

}
