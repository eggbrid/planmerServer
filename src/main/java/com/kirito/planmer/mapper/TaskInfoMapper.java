package com.kirito.planmer.mapper;

import com.kirito.planmer.bean.TaskInfo;

public interface TaskInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pm_task_info
     *
     * @mbggenerated
     */
    int insert(TaskInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pm_task_info
     *
     * @mbggenerated
     */
    int insertSelective(TaskInfo record);


    TaskInfo getTaskByToday(int user_id,long startTime,long endTime);
    TaskInfo getTaskById(int id);


    int updateTaskInfo(TaskInfo taskInfo);
}