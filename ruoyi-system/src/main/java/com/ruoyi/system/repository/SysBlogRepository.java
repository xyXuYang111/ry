package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.service.SysBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysBlogRepository {
    
    private static SysBlogService sysBlogService;

    @Autowired
    public void setSysBlogService(SysBlogService sysBlogService) {
        SysBlogRepository.sysBlogService = sysBlogService;
    }
    
    public static List<SysBlog> selectBlogList(SysBlog sysBlog){
        return sysBlogService.selectBlogList(sysBlog);
    }
}
