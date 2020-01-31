package com.ruoyi.web.controller.mongo;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.shiro.service.SysPasswordService;
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
@RequestMapping("/mongo/logininfor")
public class SysMongoLogininforController extends BaseController
{
    private String prefix = "mongoDb/logininfor";

    @Autowired
    private SysLogininforMongoService sysLogininforMongoService;

    @RequiresPermissions("mongo:logininfor:view")
    @GetMapping()
    public String logininfor()
    {
        return prefix + "/logininfor";
    }

    @RequiresPermissions("mongo:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysLogininfor logininfor)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysLogininfor> list = sysLogininforMongoService.selectLogininforList(pageNum, pageSize, logininfor);
        return getDataTable(list);
    }

    @RequiresPermissions("mongo:logininfor:remove")
    @Log(title = "mongo登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysLogininforMongoService.deleteLogininforByIds(ids));
    }
    
    @RequiresPermissions("mongo:logininfor:remove")
    @Log(title = "登陆日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        sysLogininforMongoService.cleanLogininfor();
        return success();
    }
}
