package com.lagou.controller;


import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.Test;
import com.lagou.service.CourseService;
import com.lagou.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //组合注解
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/findAll")
    public List<Test> findAll(){
        return testService.findAll();
    }



}
