package com.ruoyi.web.controller.schedule;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.service.SysScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "SCHEDULE_REDIS";

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
    @Log(title = "待办管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysSchedule schedule) {
        log.debug("新增博客记录");
        schedule.setCreateBy(ShiroUtils.getUserIdStr());
        sysScheduleService.insertSchedule(schedule);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:schedule:edit")
    @Log(title = "待办管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysSchedule schedule) {
        log.debug("修改博客记录");
        schedule.setUpdateBy(ShiroUtils.getUserIdStr());
        sysScheduleService.updateSchedule(schedule);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:schedule:remove")
    @Log(title = "待办管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysSchedule sysSchedule = new SysSchedule();
        sysSchedule.setScheduleId(ids);
        sysScheduleService.deleteScheduleByIds(sysSchedule);
        return toAjax(1);
    }

    @RequiresPermissions("system:schedule:syncRedis")
    @Log(title = "待办管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(@Validated String ids) {
        log.debug("账号同步");
        List<SysSchedule> sysScheduleList = sysScheduleService.selectScheduleList(new SysSchedule());
        //所有记录进行存储
        if(sysScheduleList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysSchedule sysSchedule : sysScheduleList){
                String str = JSON.toJSONString(sysSchedule);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:schedule:syncMongo")
    @Log(title = "待办管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(@Validated String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysSchedule> sysScheduleList = sysScheduleService.selectScheduleList(new SysSchedule());
            //所有记录进行存储
            if(sysScheduleList.size() > 0){
                for(SysSchedule sysSchedule : sysScheduleList){
                    mongoTemplate.remove(sysSchedule);
                    mongoTemplate.insert(sysSchedule);
                }
            }
        }else{
            String[] scheduleIdArray = ids.split(",");
            for(String scheduleId : scheduleIdArray){
                SysSchedule sysScheduleInfo = new SysSchedule();
                sysScheduleInfo.setScheduleId(scheduleId);

                SysSchedule sysSchedule = sysScheduleService.selectSchedule(sysScheduleInfo);
                if(sysSchedule != null){
                    mongoTemplate.remove(sysSchedule);
                    mongoTemplate.insert(sysSchedule);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "待办管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:schedule:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysSchedule operLog)
    {
        List<SysSchedule> list = sysScheduleService.selectScheduleList(operLog);
        ExcelUtil<SysSchedule> util = new ExcelUtil<SysSchedule>(SysSchedule.class);
        return util.exportExcel(list, "待办管理");
    }
}
