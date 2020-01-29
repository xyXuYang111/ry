package com.ruoyi.web.controller.daily;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.service.SysDailyService;
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
@RequestMapping("/system/daily")
@Slf4j
public class SysDailyController extends BaseController {

    @Autowired
    private SysDailyService sysDailyService;

    private String prefix = "daily";

    @RequiresPermissions("system:daily:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/daily";
    }

    @RequiresPermissions("system:daily:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDaily sysDaily)
    {
        startPage();
        List<SysDaily> list = sysDailyService.selectDailyList(sysDaily);
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
    @GetMapping("/edit/{dailyId}")
    public String edit(@PathVariable("dailyId") String dailyId, ModelMap mmap)
    {
        SysDaily sysDaily = new SysDaily();
        sysDaily.setDailyId(dailyId);
        SysDaily sysDailyInfo = sysDailyService.selectDaily(sysDaily);
        mmap.put("daily", sysDailyInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:daily:add")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDaily daily) {
        log.debug("新增博客记录");
        sysDailyService.insertDaily(daily);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:daily:edit")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDaily daily) {
        log.debug("修改博客记录");
        sysDailyService.updateDaily(daily);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:daily:remove")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysDaily sysDaily = new SysDaily();
        sysDaily.setDailyId(ids);
        sysDailyService.deleteDailyByIds(sysDaily);
        return toAjax(1);
    }
}
