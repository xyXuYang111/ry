package com.ruoyi.web.controller.file;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.service.SysFileTypeService;
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
 * @Date: 2020/1/23 09:52
 * @Description:
 */
@Controller
@RequestMapping("/system/fileType")
@Slf4j
public class SysFileTypeController extends BaseController {

    @Autowired
    private SysFileTypeService sysFileTypeService;

    private String prefix = "fileType";

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "FILE_TYPE_REDIS";

    @RequiresPermissions("system:fileType:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/fileType";
    }

    /**
     * 加载文件类型列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = sysFileTypeService.selectFileTypeTree(new SysFileType());
        return ztrees;
    }

    @RequiresPermissions("system:fileType:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysFileType> list(SysFileType fileType)
    {
        List<SysFileType> fileTypeList = sysFileTypeService.selectFileTypeList(fileType);
        return fileTypeList;
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectFileTypeTree/{fileTypeId}")
    public String selectDeptTree(@PathVariable("fileTypeId") Long fileTypeId, ModelMap mmap)
    {
        mmap.put("fileType", sysFileTypeService.selectFileTypeById(fileTypeId));
        return prefix + "/tree";
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        SysFileType fileType = new SysFileType();
        fileType.setFileTypeId(parentId);
        SysFileType fileTypeInfo = sysFileTypeService.selectFileTypeById(parentId);
        mmap.put("fileType", fileTypeInfo);
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "文件类型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:fileType:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysFileType sysFileType) {
        sysFileType.setCreateBy(ShiroUtils.getUserIdStr());
        sysFileTypeService.insertFileType(sysFileType);
        return toAjax(1);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{fileTypeId}")
    public String edit(@PathVariable("fileTypeId") Long fileTypeId, ModelMap mmap)
    {
        SysFileType sysFileType = sysFileTypeService.selectFileTypeById(fileTypeId);
        mmap.put("fileType", sysFileType);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "文件类型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:fileType:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysFileType fileType)
    {
        fileType.setUpdateBy(ShiroUtils.getUserIdStr());
        sysFileTypeService.updateFileType(fileType);
        return toAjax(1);
    }

    /**
     * 保存
     */
    @Log(title = "文件类型管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:fileType:remove")
    @GetMapping("/remove/{fileTypeId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("fileTypeId") Long fileTypeId)
    {
        SysFileType sysFileType = new SysFileType();
        sysFileType.setFileTypeId(fileTypeId);
        sysFileType.setUpdateBy(ShiroUtils.getLoginName());
        sysFileType.setStatus("1");
        sysFileType.setDelFlag("1");
        sysFileTypeService.updateFileType(sysFileType);
        return toAjax(1);
    }

    @RequiresPermissions("system:fileType:syncRedis")
    @Log(title = "账号管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(@Validated String ids) {
        log.debug("账号同步");
        List<SysFileType> sysFileTypeList = sysFileTypeService.selectFileTypeList(new SysFileType());
        //所有记录进行存储
        if(sysFileTypeList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysFileType sysFileType : sysFileTypeList){
                String str = JSON.toJSONString(sysFileType);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:fileType:syncMongo")
    @Log(title = "账号管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(@Validated String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysFileType> sysFileTypeList = sysFileTypeService.selectFileTypeList(new SysFileType());
            //所有记录进行存储
            if(sysFileTypeList.size() > 0){
                for(SysFileType sysFileType : sysFileTypeList){
                    mongoTemplate.remove(sysFileType);
                    mongoTemplate.insert(sysFileType);
                }
            }
        }else{
            String[] fileTypeIdArray = ids.split(",");
            for(String fileTypeId : fileTypeIdArray){
                SysFileType sysFileType = sysFileTypeService.selectFileTypeById(Long.valueOf(fileTypeId));
                if(sysFileType != null){
                    mongoTemplate.remove(sysFileType);
                    mongoTemplate.insert(sysFileType);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:fileType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysFileType operLog)
    {
        List<SysFileType> list = sysFileTypeService.selectFileTypeList(operLog);
        ExcelUtil<SysFileType> util = new ExcelUtil<SysFileType>(SysFileType.class);
        return util.exportExcel(list, "操作日志");
    }
}
