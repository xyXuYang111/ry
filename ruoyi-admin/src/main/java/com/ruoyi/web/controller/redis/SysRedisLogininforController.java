package com.ruoyi.web.controller.redis;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.service.SysLogininforMongoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统访问记录
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/redis/logininfor")
public class SysRedisLogininforController extends BaseController
{
    private String prefix = "redis/logininfor";

    @Autowired
    private RedisService redisService;

    @RequiresPermissions("redis:logininfor:view")
    @GetMapping()
    public String logininfor()
    {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("redis:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLogininfor logininfor)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysLogininfor> list = redisService.getObjectList("LOGIN_LOG", pageNum, pageSize);
        return getDataTable(list);
    }

    @RequiresPermissions("redis:logininfor:remove")
    @Log(title = "redis登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(1);
    }
    
    @RequiresPermissions("redis:logininfor:remove")
    @Log(title = "redis登陆日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        redisService.delete("LOGIN_LOG");;
        return success();
    }
}
