package com.ruoyi.web.controller.email;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.service.SysEmailService;
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
 * @Date: 2020/1/29 23:59
 * @Description:
 */
@Controller
@RequestMapping("/system/email")
@Slf4j
public class SysEmailController extends BaseController {

    @Autowired
    private SysEmailService sysEmailService;

    private String prefix = "email";

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String redis_code = "EMAIL_REDIS";

    @RequiresPermissions("system:email:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/email";
    }

    @RequiresPermissions("system:email:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysEmail sysEmail)
    {
        startPage();
        List<SysEmail> list = sysEmailService.selectEmailList(sysEmail);
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
    @GetMapping("/edit/{emailId}")
    public String edit(@PathVariable("emailId") String emailId, ModelMap mmap)
    {
        SysEmail sysEmail = new SysEmail();
        sysEmail.setEmailId(emailId);
        SysEmail sysEmailInfo = sysEmailService.selectEmail(sysEmail);
        mmap.put("email", sysEmailInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:email:add")
    @Log(title = "邮件管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysEmail email) {
        email.setCreateBy(ShiroUtils.getUserIdStr());
        email.setStatus("0");
        sysEmailService.insertEmail(email);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:edit")
    @Log(title = "邮件管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysEmail email) {
        email.setUpdateBy(ShiroUtils.getUserIdStr());
        sysEmailService.updateEmail(email);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:remove")
    @Log(title = "邮件管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        SysEmail sysEmail = new SysEmail();
        sysEmail.setEmailId(ids);
        sysEmailService.deleteEmailByIds(sysEmail);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:email:send")
    @Log(title = "邮件管理", businessType = BusinessType.OTHER)
    @PostMapping("/send")
    @ResponseBody
    public AjaxResult send(@Validated String ids) {
        log.debug("发送博客记录");
        sysEmailService.sendEmailByIds(ids);
        return toAjax(1);
    }

    @RequiresPermissions("system:email:syncRedis")
    @Log(title = "邮件管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(String ids) {
        log.debug("邮件管理同步");
        List<SysEmail> sysEmailList = sysEmailService.selectEmailList(new SysEmail());
        //所有记录进行存储
        if(sysEmailList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysEmail sysEmail : sysEmailList){
                String str = JSON.toJSONString(sysEmail);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:email:syncMongo")
    @Log(title = "邮件管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(String ids) {
        log.debug("邮件管理同步");
        if(ids.trim().length() == 0){
            List<SysEmail> sysEmailList = sysEmailService.selectEmailList(new SysEmail());
            //所有记录进行存储
            if(sysEmailList.size() > 0){
                for(SysEmail sysEmail : sysEmailList){
                    mongoTemplate.remove(sysEmail);
                    mongoTemplate.insert(sysEmail);
                }
            }
        }else{
            String[] emailIdArray = ids.split(",");
            for(String emailId : emailIdArray){
                SysEmail sysEmailInfo = new SysEmail();
                sysEmailInfo.setEmailId(emailId);

                SysEmail sysEmail = sysEmailService.selectEmail(sysEmailInfo);
                if(sysEmail != null){
                    mongoTemplate.remove(sysEmail);
                    mongoTemplate.insert(sysEmail);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "邮件管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:email:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysEmail operLog)
    {
        List<SysEmail> list = sysEmailService.selectEmailList(operLog);
        ExcelUtil<SysEmail> util = new ExcelUtil<SysEmail>(SysEmail.class);
        return util.exportExcel(list, "邮件管理");
    }
}
