package com.ruoyi.web.controller.blog;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.service.SysBlogTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/25 13:42
 * @Description:
 */
@Controller
@RequestMapping("/system/blogType")
@Slf4j
public class SysBlogTypeController extends BaseController {

    @Autowired
    private SysBlogTypeService sysBlogTypeService;

    private String prefix = "blogType";

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "BLOG_TYPE_REDIS";

    @RequiresPermissions("system:blogType:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/blogType";
    }

    /**
     * 加载文件类型列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = sysBlogTypeService.selectBlogTypeTree(new SysBlogType());
        return ztrees;
    }

    @RequiresPermissions("system:blogType:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysBlogType> list(SysBlogType blogType)
    {
        List<SysBlogType> blogTypeList = sysBlogTypeService.selectBlogTypeList(blogType);
        return blogTypeList;
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectBlogTypeTree/{blogTypeId}")
    public String selectDeptTree(@PathVariable("blogTypeId") Long blogTypeId, ModelMap mmap)
    {
        mmap.put("blogType", sysBlogTypeService.selectBlogTypeById(blogTypeId));
        return prefix + "/tree";
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        SysBlogType blogType = new SysBlogType();
        blogType.setBlogTypeId(parentId);
        SysBlogType blogTypeInfo = sysBlogTypeService.selectBlogTypeById(parentId);
        mmap.put("blogType", blogTypeInfo);
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "博客类型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:blogType:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysBlogType sysBlogType) {
        sysBlogType.setCreateBy(ShiroUtils.getUserIdStr());
        sysBlogTypeService.insertBlogType(sysBlogType);
        return toAjax(1);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{blogTypeId}")
    public String edit(@PathVariable("blogTypeId") Long blogTypeId, ModelMap mmap)
    {
        SysBlogType sysBlogType = sysBlogTypeService.selectBlogTypeById(blogTypeId);
        mmap.put("blogType", sysBlogType);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "博客类型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:blogType:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysBlogType blogType)
    {
        blogType.setUpdateBy(ShiroUtils.getLoginName());
        blogType.setUpdateBy(ShiroUtils.getUserIdStr());
        sysBlogTypeService.updateBlogType(blogType);
        return toAjax(1);
    }

    /**
     * 保存
     */
    @Log(title = "博客类型管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:blogType:remove")
    @GetMapping("/remove/{blogTypeId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("blogTypeId") Long blogTypeId)
    {
        SysBlogType sysBlogType = new SysBlogType();
        sysBlogType.setBlogTypeId(blogTypeId);
        sysBlogType.setUpdateBy(ShiroUtils.getLoginName());
        sysBlogType.setStatus("1");
        sysBlogType.setDelFlag("1");
        sysBlogType.setCreateBy(ShiroUtils.getUserIdStr());
        sysBlogTypeService.updateBlogType(sysBlogType);
        return toAjax(1);
    }

    @RequiresPermissions("system:blogType:syncRedis")
    @Log(title = "博客类型管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(String ids) {
        log.debug("账号同步");
        List<SysBlogType> sysBlogTypeList = sysBlogTypeService.selectBlogTypeList(new SysBlogType());
        //所有记录进行存储
        if(sysBlogTypeList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysBlogType sysBlogType : sysBlogTypeList){
                String str = JSON.toJSONString(sysBlogType);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:blogType:syncMongo")
    @Log(title = "博客类型管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysBlogType> sysBlogTypeList = sysBlogTypeService.selectBlogTypeList(new SysBlogType());
            //所有记录进行存储
            if(sysBlogTypeList.size() > 0){
                for(SysBlogType sysBlogType : sysBlogTypeList){
                    mongoTemplate.remove(sysBlogType);
                    mongoTemplate.insert(sysBlogType);
                }
            }
        }else{
            String[] blogTypeIdArray = ids.split(",");
            for(String blogTypeId : blogTypeIdArray){
                SysBlogType sysBlogTypeInfo = new SysBlogType();
                sysBlogTypeInfo.setBlogTypeId(Long.valueOf(blogTypeId));

                SysBlogType sysBlogType = sysBlogTypeService.selectBlogTypeById(Long.valueOf(blogTypeId));
                if(sysBlogType != null){
                    mongoTemplate.remove(sysBlogType);
                    mongoTemplate.insert(sysBlogType);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "博客类型管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:blogType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBlogType sysBlogType)
    {
        List<SysBlogType> list = sysBlogTypeService.selectBlogTypeList(sysBlogType);
        ExcelUtil<SysBlogType> util = new ExcelUtil<SysBlogType>(SysBlogType.class);
        return util.exportExcel(list, "博客类型管理");
    }
}
