package com.ruoyi.web.controller.account;

import com.alibaba.fastjson.JSON;
import com.mongodb.Mongo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.code.QRCodeUtil;
import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.service.SysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/29 16:31
 * @Description:
 */
@Controller
@RequestMapping("/system/account")
@Slf4j
public class SysAccountController extends BaseController {

    @Autowired
    private SysAccountService sysAccountService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private String prefix = "account";

    private final String redis_code = "ACCOUNT_REDIS";

    @RequiresPermissions("system:account:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/account";
    }

    @RequiresPermissions("system:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAccount sysAccount)
    {
        startPage();
        List<SysAccount> list = sysAccountService.selectAccountList(sysAccount);
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
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") String accountId, ModelMap mmap)
    {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setAccountId(accountId);
        SysAccount sysAccountInfo = sysAccountService.selectAccount(sysAccount);
        mmap.put("account", sysAccountInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:account:add")
    @Log(title = "账号管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysAccount account) {
        account.setCreateBy(ShiroUtils.getUserIdStr());
        sysAccountService.insertAccount(account);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:account:edit")
    @Log(title = "账号管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysAccount account) {
        log.debug("修改博客记录");
        account.setUpdateBy(ShiroUtils.getUserIdStr());
        sysAccountService.updateAccount(account);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:account:remove")
    @Log(title = "账号管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        log.debug("删除博客记录");
        SysAccount sysAccount = new SysAccount();
        sysAccount.setAccountId(ids);
        sysAccountService.deleteAccountByIds(sysAccount);
        return toAjax(1);
    }

    @Log(title = "账号管理", businessType = BusinessType.OTHER)
    @GetMapping(value="/code")
    public void showPic(HttpServletResponse response, @Validated String ids){

        SysAccount sysAccount = new SysAccount();
        sysAccount.setAccountId(ids);
        SysAccount sysAccountInfo = sysAccountService.selectAccount(sysAccount);

        //本地生成二维码
        String fileUrl= QRCodeUtil.createQrCodePhoto(sysAccountInfo.toString());

        File file = new File(fileUrl);

        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
                "attachment;fileName=" + file.getName());// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresPermissions("system:account:syncRedis")
    @Log(title = "账号管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncRedis")
    @ResponseBody
    public AjaxResult syncRedis(String ids) {
        log.debug("账号同步");
        List<SysAccount> sysAccountList = sysAccountService.selectAccountList(new SysAccount());
        //所有记录进行存储
        if(sysAccountList.size() > 0){
            RedisRepository.delete(redis_code);
            for(SysAccount sysAccount : sysAccountList){
                String str = JSON.toJSONString(sysAccount);
                RedisRepository.leftPush(redis_code, str);
            }
        }
        return toAjax(1);
    }

    @RequiresPermissions("system:account:syncMongo")
    @Log(title = "账号管理", businessType = BusinessType.OTHER)
    @PostMapping("/syncMongo")
    @ResponseBody
    public AjaxResult syncMongo(String ids) {
        log.debug("账号同步");
        if(ids.trim().length() == 0){
            List<SysAccount> sysAccountList = sysAccountService.selectAccountList(new SysAccount());
            //所有记录进行存储
            if(sysAccountList.size() > 0){
                for(SysAccount sysAccount : sysAccountList){
                    mongoTemplate.remove(sysAccount);
                    mongoTemplate.insert(sysAccount);
                }
            }
        }else{
            String[] accountIdArray = ids.split(",");
            for(String accountId : accountIdArray){
                SysAccount sysAccountInfo = new SysAccount();
                sysAccountInfo.setAccountId(accountId);

                SysAccount sysAccount = sysAccountService.selectAccount(sysAccountInfo);
                if(sysAccount != null){
                    mongoTemplate.remove(sysAccount);
                    mongoTemplate.insert(sysAccount);
                }
            }
        }
        return toAjax(1);
    }

    @Log(title = "账号管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:account:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysAccount account)
    {
        List<SysAccount> list = sysAccountService.selectAccountList(account);
        ExcelUtil<SysAccount> util = new ExcelUtil<SysAccount>(SysAccount.class);
        return util.exportExcel(list, "账号管理");
    }
}
