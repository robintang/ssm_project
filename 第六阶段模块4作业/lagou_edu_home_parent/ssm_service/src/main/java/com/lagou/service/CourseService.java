package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    /*多条件列表查询*/
    public List<Course> findCourseByCondition(CourseVo courseVo);

    /*新增课程信息 新增讲师信息*/
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;


    public CourseVo findCourseById(Integer id);

    /*更新课程及讲师信息*/
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;

    /**
     * 修改课程状态
     * */
    public void updateCourseStatus(Integer id,Integer status);

}

