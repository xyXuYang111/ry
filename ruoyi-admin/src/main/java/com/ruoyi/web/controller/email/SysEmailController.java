package com.ruoyi.web.controller.email;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.service.SysEmailService;
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
 * @Date: 2020/1/29 23:59
 * @Description:
 */
@Controller
@RequestMapping("/system/email")
@Slf4j
public class SysEmailController extends BaseController {

    @Autowired
    private SysEmailService sysEmailService;

    private String prefix = "email";

    @RequiresPermissions("system:email:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/email";
    }

    @RequiresPermissions("system:email:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysEmail sysEmail)
    {
        startPage();
        List<SysEmail> list = sysEmailService.selectEmailList(sysEmail);
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
    @GetMapping("/edit/{emailId}")
    public String edit(@PathVariable("emailId") String emailId, ModelMap mmap)
    {
        SysEmail sysEmail = new SysEmail();
        sysEmail.setEmailId(emailId);
        SysEmail sysEmailInfo = sysEmailService.selectEmail(sysEmail);
        mmap.put("email", sysEmailInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:email:add")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysEmail email) {
        log.debug("新增博客记录");
        sysEmailService.insertEmail(email);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:edit")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysEmail email) {
        log.debug("修改博客记录");
        sysEmailService.updateEmail(email);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:remove")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysEmail sysEmail = new SysEmail();
        sysEmail.setEmailId(ids);
        sysEmailService.deleteEmailByIds(sysEmail);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:send")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/send")
    @ResponseBody
    public AjaxResult send(@Validated String ids) {
        log.debug("发送博客记录");
        sysEmailService.sendEmailByIds(ids);
        return toAjax(1);
    }
}
