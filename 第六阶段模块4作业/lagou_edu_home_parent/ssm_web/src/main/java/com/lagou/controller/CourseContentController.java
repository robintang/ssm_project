package com.lagou.controller;


import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;


    /**
     * 查询课程内容 * */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam int courseId){
        try {
            //调用service
            List<CourseSection> sectionList =
                    courseContentService.findSectionAndLessonByCourseId(courseId);
            //封装数据并返回
            ResponseResult result = new ResponseResult(true,200,"响应成功",sectionList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 回显章节对应的课程信息 * */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam int courseId){
        try { //调用service
            Course course = courseContentService.findCourseByCourseId(courseId);
            ResponseResult result = new ResponseResult(true,200,"响应成功",course);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*新增以及更新章节信息*/
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section) {

        if (section.getId() == null){
            courseContentService.saveSection(section);
            return new ResponseResult(true,200,"添加响应成功",null);
        }else {
            courseContentService.updateSection(section);
            return new ResponseResult(true,200,"更新响应成功",null);
        }
    }

    /**
     * 修改章节状态
     * 状态，0:隐藏;1:待更新;2:已发布
     * */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(@RequestParam int id,@RequestParam int status){
        try {
            courseContentService.updateSectionStatus(id,status);
            //封装最新的状态信息
            Map<String,Integer> map = new HashMap<>(); map.put("status",status);
            return new ResponseResult(true,200,"响应成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*新增课时信息*/
    @RequestMapping("/saveLesson")
    public ResponseResult saveLesson(CourseLesson lesson){
        courseContentService.saveLesson(lesson);
        return new ResponseResult(true,200,"响应成功",null);
    }
}
