package com.kirito.planmer.mapper;

import com.kirito.planmer.bean.TaskWithBLOBs;

import java.util.List;

public interface TaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pm_task
     *
     * @mbggenerated
     */
    int insert(TaskWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pm_task
     *
     * @mbggenerated
     */
    int insertSelective(TaskWithBLOBs record);


   List<TaskWithBLOBs> getTaskByUserId(int userId, long time);
}