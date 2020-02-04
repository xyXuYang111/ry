package com.ruoyi.web.controller.redis;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.service.SysOperLogMongoDbService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/redis/operlog")
public class SysRedisOperlogController extends BaseController
{
    private String prefix = "redis/operlog";

    @Autowired
    private RedisService redisService;

    @RequiresPermissions("redis:operlog:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/operlog";
    }

    @RequiresPermissions("redis:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLog operLog)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<String> list = redisService.getObjectList("OPERATE_LOG", pageNum, pageSize);
        List<SysOperLog> sysChatInfoList = new ArrayList<>();
        if(list.size() > 0){
            for(String str : list){
                SysOperLog redisChat = JSON.parseObject(str, SysOperLog.class);
                sysChatInfoList.add(redisChat);
            }
        }
        return getDataTable(sysChatInfoList);
    }

    @RequiresPermissions("redis:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(1);
    }

    @RequiresPermissions("redis:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") String operId, ModelMap mmap)
    {
        SysOperLog operLog = new SysOperLog();
        operLog.setOperId(operId);
        //mmap.put("operLog", sysOperLogMongoDbService.selectOperLogById(operLog));
        return prefix + "/detail";
    }
    
    @Log(title = "redis操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("redis:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        redisService.delete("OPERATE_LOG");;
        return success();
    }
}
