package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.service.SysBlogService;
import com.ruoyi.system.service.SysBlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysBlogTypeRepository {
    
    private static SysBlogTypeService sysBlogTypeService;

    @Autowired
    public void setSysBlogTypeService(SysBlogTypeService sysBlogTypeService) {
        SysBlogTypeRepository.sysBlogTypeService = sysBlogTypeService;
    }
    
    public static List<SysBlogType> selectBlogTypeList(SysBlogType sysBlogType){
        return sysBlogTypeService.selectBlogTypeList(sysBlogType);
    }
}
