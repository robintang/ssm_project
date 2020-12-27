package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/promotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    /*分页广告查询*/
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVO promotionAdVO){
        PageInfo pageInfo = promotionAdService.findAllPromotionByPage(promotionAdVO);
        return new ResponseResult(true,200,"广告查询分页成功",pageInfo);
    }

    /*课程图片上传*/
    @RequestMapping("/promotionAdUpload")
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

        return new ResponseResult(true,200,"添加广告图片成功",map);

    }


    /*广告动态上下线*/
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(int id,int status){

        promotionAdService.updatePromotionAdStatus(id,status);

        return new ResponseResult(true,200,"广告动态上下线成功",null);
    }

    /*新增或更新广告位置*/
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        if (promotionAd.getId() == null){
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true,200,"新增广告成功",null);
        }else {
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true,200,"更新广告成功",null);
        }
    }

    /**
     * 根据id回显 广告数据 * */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(@RequestParam int id){
        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
        return new ResponseResult(true,200,"查询成功",promotionAd);
    }


}
