package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    /**
     * 查询课程信息&条件查询 接口 * */
    @RequestMapping("/findCourseByConditioin")
    public ResponseResult findCourseByConditioin(@RequestBody CourseVo courseVO)
    {
        List<Course> courseList =
                courseService.findCourseByCondition(courseVO);
        ResponseResult result = new ResponseResult(true,200,"响应成功",courseList);
        return result;
    }


    /*课程图片上传*/
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        /*判断接收文件是否惟空*/
        if (file.isEmpty()){
            throw new RuntimeException();
        }

        //获取项目部署路径
        String realPath = request.getServletContext().getRealPath("/");

        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //获取文件名
        String filename = file.getOriginalFilename();

        //生成新文件名
      String newFilename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));


        //文件上传
        String uploadPath = substring + "upload";
        File filePath = new File(uploadPath, newFilename);

        //如果目录不存在
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();//创建目录
        }

        //图片上传
        file.transferTo(filePath);

        //将文件名和路经响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFilename);
        map.put("filePath","http://localhost:8080/upload/"+newFilename);

        return new ResponseResult(true,200,"图片上传成功",map);

    }

    /*新增课程信息讲师信息
    新增和修改写在一个方法中
    */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo){

        if (courseVo.getId()==null){
        try {
            courseService.saveCourseOrTeacher(courseVo);
            ResponseResult responseResult = new ResponseResult(true,200,"新增响应成功",null);
            return responseResult;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
            }
        }
        else {
            try {
                courseService.updateCourseOrTeacher(courseVo);
                return new ResponseResult(true,200,"修改响应成功",null);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /*查询课程 by id*/
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        CourseVo course = courseService.findCourseById(id);
        return new ResponseResult(true,200,"响应成功",course);
    }


    /*更换课程状态*/
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status){
        courseService.updateCourseStatus(id,status);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"响应成功",map);
    }


}
