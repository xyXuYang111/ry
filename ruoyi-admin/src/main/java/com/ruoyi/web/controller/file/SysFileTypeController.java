package com.ruoyi.web.controller.file;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.service.SysFileTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
        sysFileType.setCreateBy(ShiroUtils.getLoginName());
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
        fileType.setUpdateBy(ShiroUtils.getLoginName());
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
}
