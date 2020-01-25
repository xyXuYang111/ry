package com.ruoyi.web.controller.schedule;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.service.SysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 14:52
 * @Description:
 */
@Controller
@RequestMapping("/system/schedule")
@Slf4j
public class SysScheduleController extends BaseController {
    
    @Autowired
    private SysScheduleService sysScheduleService;

    private String prefix = "schedule";

    @RequiresPermissions("system:schedule:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/schedule";
    }

    @RequiresPermissions("system:schedule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysSchedule sysSchedule)
    {
        startPage();
        List<SysSchedule> list = sysScheduleService.selectScheduleList(sysSchedule);
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
    @GetMapping("/edit/{scheduleId}")
    public String edit(@PathVariable("scheduleId") String scheduleId, ModelMap mmap)
    {
        SysSchedule sysSchedule = new SysSchedule();
        sysSchedule.setScheduleId(scheduleId);
        SysSchedule sysScheduleInfo = sysScheduleService.selectSchedule(sysSchedule);
        mmap.put("schedule", sysScheduleInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:schedule:add")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysSchedule schedule) {
        log.debug("新增博客记录");
        sysScheduleService.insertSchedule(schedule);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:schedule:edit")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysSchedule schedule) {
        log.debug("修改博客记录");
        sysScheduleService.updateSchedule(schedule);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:schedule:remove")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysSchedule sysSchedule = new SysSchedule();
        sysSchedule.setScheduleId(ids);
        sysScheduleService.deleteScheduleByIds(sysSchedule);
        return toAjax(1);
    }
}
