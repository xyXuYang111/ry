package com.ruoyi.web.controller.mongo;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysMongoDb;
import com.ruoyi.system.service.SysMongoDbService;
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
 * @Date: 2020/1/29 11:30
 * @Description:
 */
@Controller
@RequestMapping("/system/mongoDb")
@Slf4j
public class SysMongoDbController extends BaseController {

    @Autowired
    private SysMongoDbService sysMongoDbService;

    private String prefix = "mongoDb";

    @RequiresPermissions("system:mongoDb:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/mongoDb";
    }

    @RequiresPermissions("system:mongoDb:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMongoDb sysMongoDb)
    {
        String name = sysMongoDb.getName();
        String title = sysMongoDb.getTitle();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        List<SysMongoDb> list = sysMongoDbService.getPageList(pageNum, pageSize, name, title);
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
    @GetMapping("/edit/{mongoDbId}")
    public String edit(@PathVariable("mongoDbId") String mongoDbId, ModelMap mmap)
    {
        SysMongoDb sysMongoDb = new SysMongoDb();
        sysMongoDb.setId(mongoDbId);
        SysMongoDb sysMongoDbInfo = sysMongoDbService.getSysMongoDbById(mongoDbId);
        mmap.put("mongoDb", sysMongoDbInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:mongoDb:add")
    @Log(title = "mongo管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysMongoDb mongoDb) {
        log.debug("新增博客记录");
        sysMongoDbService.SaveOrUpdateSysMongoDb(mongoDb);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:mongoDb:edit")
    @Log(title = "mongo管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysMongoDb mongoDb) {
        log.debug("修改博客记录");
        sysMongoDbService.SaveOrUpdateSysMongoDb(mongoDb);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:mongoDb:remove")
    @Log(title = "mongo管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysMongoDb sysMongoDb = new SysMongoDb();
        sysMongoDb.setId(ids);
        sysMongoDbService.deleteSysMongoDb(ids);
        return toAjax(1);
    } 
}
