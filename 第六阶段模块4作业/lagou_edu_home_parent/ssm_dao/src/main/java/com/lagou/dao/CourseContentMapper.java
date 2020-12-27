package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentMapper {
    /*根据id查询关联的章节信息及课时信息*/
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /* 回显章节对应的课程信息 */
    public Course findCourseByCourseId(int courseId);

    /* 保存章节  */
    public void saveSection(CourseSection section);

    /*更新章节*/
    public void updateSection(CourseSection section);

    /*修改章节状态*/
    public void updateSectionStatus(CourseSection section);

    /*保存课时*/
    public void saveLesson(CourseLesson lesson);
}
