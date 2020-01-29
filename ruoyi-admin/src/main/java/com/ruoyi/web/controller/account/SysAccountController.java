package com.ruoyi.web.controller.account;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.service.SysAccountService;
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
 * @Date: 2020/1/29 16:31
 * @Description:
 */
@Controller
@RequestMapping("/system/account")
@Slf4j
public class SysAccountController extends BaseController {

    @Autowired
    private SysAccountService sysAccountService;

    private String prefix = "account";

    @RequiresPermissions("system:account:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/account";
    }

    @RequiresPermissions("system:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAccount sysAccount)
    {
        startPage();
        List<SysAccount> list = sysAccountService.selectAccountList(sysAccount);
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
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") String accountId, ModelMap mmap)
    {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setAccountId(accountId);
        SysAccount sysAccountInfo = sysAccountService.selectAccount(sysAccount);
        mmap.put("account", sysAccountInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:account:add")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysAccount account) {
        log.debug("新增博客记录");
        sysAccountService.insertAccount(account);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:account:edit")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysAccount account) {
        log.debug("修改博客记录");
        sysAccountService.updateAccount(account);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:account:remove")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysAccount sysAccount = new SysAccount();
        sysAccount.setAccountId(ids);
        sysAccountService.deleteAccountByIds(sysAccount);
        return toAjax(1);
    }
}
