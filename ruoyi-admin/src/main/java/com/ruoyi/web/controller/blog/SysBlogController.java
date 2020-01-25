package com.ruoyi.web.controller.blog;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.SysBlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
        sysBlogService.insertBlog(blog);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:blog:edit")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysBlog blog) {
        log.debug("修改博客记录");
        sysBlogService.updateBlog(blog);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:blog:remove")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysBlog sysBlog = new SysBlog();
        sysBlog.setBlogId(ids);
        sysBlogService.deleteBlogByIds(sysBlog);
        return toAjax(1);
    }
}
