package com.ruoyi.web.controller.file;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.domain.server.SysFile;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.service.SysFileInfoService;
import com.ruoyi.system.service.SysFileTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 00:36
 * @Description:
 */
@Controller
@RequestMapping("/system/file")
@Slf4j
public class SysFileInfoController extends BaseController {

    private String prefix = "file";

    @Autowired
    private SysFileInfoService sysFileInfoService;

    @Autowired
    private SysFileTypeService sysFileTypeService;

    @RequiresPermissions("system:fileType:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/file";
    }

    @RequiresPermissions("system:fileType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysFileInfo fileType)
    {
        List<SysFileInfo> list = sysFileInfoService.selectFileList(fileType);
        return getDataTable(list);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectFileTree/{fileTypeId}")
    public String selectDeptTree(@PathVariable("fileTypeId") String fileTypeId, ModelMap mmap)
    {
        SysFileInfo fileType = new SysFileInfo();
        fileType.setFileTypeId(fileTypeId);
        mmap.put("fileType", sysFileInfoService.selectFileInfo(fileType));
        return prefix + "/tree";
    }

    /**
     * 新增部门
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        SysFileType fileTypeInfo = sysFileTypeService.selectFileTypeById(1L);
        mmap.put("fileType", fileTypeInfo);
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "类型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:fileType:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysFileInfo sysFile) throws Exception{
        sysFile.setCreateBy(ShiroUtils.getLoginName());
        CommonsMultipartFile file = sysFile.getFile();
        String path="F:\\file\\"+ file.getOriginalFilename();
        File newFile=new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        sysFile.setFileUrl(path);
        sysFileInfoService.insertFile(sysFile);
        return toAjax(1);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") String fileId, ModelMap mmap)
    {
        SysFileInfo fileType = new SysFileInfo();
        fileType.setFileId(fileId);
        SysFileInfo sysFile = sysFileInfoService.selectFileInfo(fileType);
        mmap.put("file", sysFile);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "类型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:fileType:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysFileInfo fileType)
    {
        fileType.setUpdateBy(ShiroUtils.getLoginName());
        sysFileInfoService.updateFile(fileType);
        return toAjax(1);
    }

    /**
     * 保存
     */
    @Log(title = "类型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:fileType:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids)
    {
        SysFileInfo sysFile = new SysFileInfo();
        sysFile.setFileId(ids);
        sysFileInfoService.deleteFile(sysFile);
        return toAjax(1);
    }
}
