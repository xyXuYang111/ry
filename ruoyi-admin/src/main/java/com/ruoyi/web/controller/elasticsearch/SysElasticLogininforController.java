package com.ruoyi.web.controller.elasticsearch;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysLogininforElastic;
import com.ruoyi.system.service.SysLogininforElasticService;
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
@RequestMapping("/elastic/logininfor")
public class SysElasticLogininforController extends BaseController
{
    private String prefix = "elastic/logininfor";

    @Autowired
    private SysLogininforElasticService sysLogininforElasticService;

    @RequiresPermissions("elastic:logininfor:view")
    @GetMapping()
    public String logininfor()
    {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("elastic:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLogininforElastic logininfor)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysLogininforElastic> list = sysLogininforElasticService.selectLogininforList(pageNum, pageSize, logininfor);
        return getDataTable(list);
    }

    @RequiresPermissions("elastic:logininfor:remove")
    @Log(title = "elastic登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysLogininforElasticService.deleteLogininforByIds(ids));
    }
    
    @RequiresPermissions("elastic:logininfor:remove")
    @Log(title = "登陆日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        sysLogininforElasticService.cleanLogininfor();
        return success();
    }
}
