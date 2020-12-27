package com.lagou.controller;

import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @RequestMapping("findAllResourceCategory")
    public ResponseResult findAllResourceCategory(){
        List<ResourceCategory> categoryList = resourceCategoryService.findAllResourceCategory();
        return new ResponseResult(true,200,"查询成功",categoryList);
    }

    /*添加&修改资源分类*/
    @RequestMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory){
        if (resourceCategory.getId() == null){
            resourceCategoryService.saveResourceCategory(resourceCategory);
            return new ResponseResult(true,200,"新增资源分类成功",null);
        }else {
            resourceCategoryService.updateResourceCategory(resourceCategory);
            return new ResponseResult(true,200,"修改资源分类成功",null);
        }
    }

    /*删除资源分类*/
    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(@RequestParam Integer id){
        resourceCategoryService.deleteResourceCategory(id);
        return new ResponseResult(true,200,"删除资源分类成功",null);
    }

}
