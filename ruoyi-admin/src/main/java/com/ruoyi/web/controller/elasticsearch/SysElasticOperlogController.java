package com.ruoyi.web.controller.elasticsearch;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysOperLogElastic;
import com.ruoyi.system.service.SysOperLogElasticService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/elastic/operlog")
public class SysElasticOperlogController extends BaseController
{
    private String prefix = "elastic/operlog";

    @Autowired
    private SysOperLogElasticService sysOperLogElasticService;

    @RequiresPermissions("elastic:operlog:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/operlog";
    }

    @RequiresPermissions("elastic:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLogElastic operLog)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysOperLogElastic> list = sysOperLogElasticService.selectOperLogList(pageNum, pageSize, operLog);
        return getDataTable(list);
    }

    @RequiresPermissions("elastic:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysOperLogElasticService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("elastic:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") String operId, ModelMap mmap)
    {
        SysOperLogElastic operLog = new SysOperLogElastic();
        operLog.setOperId(operId);
        mmap.put("operLog", sysOperLogElasticService.selectOperLogById(operLog));
        return prefix + "/detail";
    }
    
    @Log(title = "elastic操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("elastic:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        sysOperLogElasticService.cleanOperLog();
        return success();
    }
}
