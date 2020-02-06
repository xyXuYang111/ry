package com.ruoyi.web.controller.blog;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.SysBlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:04
 * @Description:
 */
@Controller
@RequestMapping("/system/blog")
@Slf4j
public class SysBlogController extends BaseController {

    private String prefix = "blog";

    @Autowired
    private SysBlogService sysBlogService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "BLOG_REDIS";

    @RequiresPermissions("system:blog:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/blog";
    }

    @RequiresPermissions("system:blog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysBlog sysBlog, Model model)
    {
        startPage();
        List<SysBlog> list = sysBlogService.selectBlogList(sysBlog);
        return getDataTable(list);
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{blogId}")
    public String edit(@PathVariable("blogId") String blogId, ModelMap mmap, Model model)
    {
        SysBlog sysBlog = new SysBlog();
        sysBlog.setBlogId(blogId);
        SysBlog sysBlogInfo = sysBlogService.selectBlog(sysBlog);
        mmap.put("blog", sysBlogInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:blog:add")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysBlog blog) {
        log.debug("新增博客记录");
        blog.setCreateBy(ShiroUtils.getUserIdStr());
        sysBlogService.insertBlog(blog);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:blog:edit")
    @Log(title = "博客管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysBlog blog) {
        log.debug("修改博客记录");
        blog.setUpdateBy(ShiroUtils.getUserIdStr());
        sysBlogService.updateBlog(blog);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:blog:remove")
    @Log(title = "博客管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysBlog sysBlog = new SysBlog();
        sysBlog.setBlogId(ids);
        sysBlogService.deleteBlogByIds(sysBlog);
        return toAjax(1);
    }

    @RequiresPermissions("system:blog:syncRedis")
    @Log(title = "博客管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(String ids) {
        log.debug("账号同步");
        List<SysBlog> sysBlogList = sysBlogService.selectBlogList(new SysBlog());
        //所有记录进行存储
        if(sysBlogList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysBlog sysBlog : sysBlogList){
                String str = JSON.toJSONString(sysBlog);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:blog:syncMongo")
    @Log(title = "博客管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysBlog> sysBlogList = sysBlogService.selectBlogList(new SysBlog());
            //所有记录进行存储
            if(sysBlogList.size() > 0){
                for(SysBlog sysBlog : sysBlogList){
                    mongoTemplate.remove(sysBlog);
                    mongoTemplate.insert(sysBlog);
                }
            }
        }else{
            String[] blogIdArray = ids.split(",");
            for(String blogId : blogIdArray){
                SysBlog sysBlogInfo = new SysBlog();
                sysBlogInfo.setBlogId(blogId);

                SysBlog sysBlog = sysBlogService.selectBlog(sysBlogInfo);
                if(sysBlog != null){
                    mongoTemplate.remove(sysBlog);
                    mongoTemplate.insert(sysBlog);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "博客管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:blog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBlog sysBlog)
    {
        List<SysBlog> list = sysBlogService.selectBlogList(sysBlog);
        ExcelUtil<SysBlog> util = new ExcelUtil<SysBlog>(SysBlog.class);
        return util.exportExcel(list, "博客管理");
    }
}
