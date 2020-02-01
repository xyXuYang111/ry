package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.Md5Utils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysBlogServiceImpl implements SysBlogService {

    private static final String BLOG_CACHE_KEY = "blog";

    @Autowired
    private SysBlogMapper sysBlogMapper;

    @Override
    @Cacheable(value = BLOG_CACHE_KEY, key = "'selectBlog'+ #sysBlog.blogId")
    public SysBlog selectBlog(SysBlog sysBlog) {
        return sysBlogMapper.selectBlog(sysBlog);
    }

    @Override
    @Cacheable(value = BLOG_CACHE_KEY, key = "'selectBlogList'+ #sysBlog.toString()")
    public List<SysBlog> selectBlogList(SysBlog sysBlog) {
        return sysBlogMapper.selectBlogList(sysBlog);
    }

    @Override
    @Cacheable(value = BLOG_CACHE_KEY, key = "'checkBlogKeyUnique'+ #sysBlogKey")
    public SysBlog checkBlogKeyUnique(String sysBlogKey) {
        return sysBlogMapper.checkBlogKeyUnique(sysBlogKey);
    }

    @Override
    @CacheEvict(value = BLOG_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertBlog(SysBlog sysBlog) {
        sysBlogMapper.insertBlog(sysBlog);
    }

    @Override
    @CacheEvict(value = BLOG_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateBlog(SysBlog sysBlog) {
        sysBlogMapper.updateBlog(sysBlog);
    }

    @Override
    @CacheEvict(value = BLOG_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteBlogByIds(SysBlog sysBlog) {
        sysBlogMapper.deleteBlogByIds(sysBlog);
    }
}
