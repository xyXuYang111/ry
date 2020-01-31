package com.ruoyi.web.controller.mongo;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.service.SysOperLogMongoDbService;
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
@RequestMapping("/mongo/operlog")
public class SysMongoOperlogController extends BaseController
{
    private String prefix = "mongoDb/operlog";

    @Autowired
    private SysOperLogMongoDbService sysOperLogMongoDbService;

    @RequiresPermissions("mongo:operlog:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/operlog";
    }

    @RequiresPermissions("mongo:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLog operLog)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysOperLog> list = sysOperLogMongoDbService.selectOperLogList(pageNum, pageSize, operLog);
        return getDataTable(list);
    }

    @RequiresPermissions("mongo:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysOperLogMongoDbService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("mongo:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") String operId, ModelMap mmap)
    {
        SysOperLog operLog = new SysOperLog();
        operLog.setOperId(operId);
        mmap.put("operLog", sysOperLogMongoDbService.selectOperLogById(operLog));
        return prefix + "/detail";
    }
    
    @Log(title = "mongo操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("mongo:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        sysOperLogMongoDbService.cleanOperLog();
        return success();
    }
}
