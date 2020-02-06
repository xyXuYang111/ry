package com.ruoyi.web.controller.daily;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.service.SysDailyService;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "DAILY_REDIS";

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
    @Log(title = "日记管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDaily daily) {
        log.debug("新增博客记录");
        daily.setCreateBy(ShiroUtils.getUserIdStr());
        sysDailyService.insertDaily(daily);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:daily:edit")
    @Log(title = "日记管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDaily daily) {
        daily.setUpdateBy(ShiroUtils.getUserIdStr());
        sysDailyService.updateDaily(daily);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:daily:remove")
    @Log(title = "日记管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        SysDaily sysDaily = new SysDaily();
        sysDaily.setDailyId(ids);
        sysDailyService.deleteDailyByIds(sysDaily);
        return toAjax(1);
    }

    @RequiresPermissions("system:daily:syncRedis")
    @Log(title = "日志管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(String ids) {
        log.debug("账号同步");
        List<SysDaily> sysDailyList = sysDailyService.selectDailyList(new SysDaily());
        //所有记录进行存储
        if(sysDailyList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysDaily sysDaily : sysDailyList){
                String str = JSON.toJSONString(sysDaily);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:daily:syncMongo")
    @Log(title = "日志管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysDaily> sysDailyList = sysDailyService.selectDailyList(new SysDaily());
            //所有记录进行存储
            if(sysDailyList.size() > 0){
                for(SysDaily sysDaily : sysDailyList){
                    mongoTemplate.remove(sysDaily);
                    mongoTemplate.insert(sysDaily);
                }
            }
        }else{
            String[] dailyIdArray = ids.split(",");
            for(String dailyId : dailyIdArray){
                SysDaily sysDailyInfo = new SysDaily();
                sysDailyInfo.setDailyId(dailyId);

                SysDaily sysDaily = sysDailyService.selectDaily(sysDailyInfo);
                if(sysDaily != null){
                    mongoTemplate.remove(sysDaily);
                    mongoTemplate.insert(sysDaily);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "日志管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:daily:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDaily operLog)
    {
        List<SysDaily> list = sysDailyService.selectDailyList(operLog);
        ExcelUtil<SysDaily> util = new ExcelUtil<SysDaily>(SysDaily.class);
        return util.exportExcel(list, "日志管理");
    }
}
