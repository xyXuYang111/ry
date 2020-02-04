package com.ruoyi.web.controller.chat;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysChatCode;
import com.ruoyi.system.service.SysChatCodeService;
import com.ruoyi.system.service.SysChatCodeService;
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
 * @Date: 2020/1/29 16:31
 * @Description:
 */
@Controller
@RequestMapping("/system/chat")
@Slf4j
public class SysChatController extends BaseController {

    @Autowired
    private SysChatCodeService sysChatCodeService;

    private String prefix = "chat";

    @RequiresPermissions("system:chat:list")
    @GetMapping()
    public String operlog() {
        log.debug("登录主界面");
        return prefix + "/chat";
    }

    @RequiresPermissions("system:chat:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysChatCode sysChatCode)
    {
        startPage();
        List<SysChatCode> list = sysChatCodeService.selectChatCodeList(sysChatCode);
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
    @GetMapping("/edit/{chatId}")
    public String edit(@PathVariable("chatId") String chatId, ModelMap mmap)
    {
        SysChatCode sysChatCode = new SysChatCode();
        sysChatCode.setChatId(chatId);
        SysChatCode sysChatCodeInfo = sysChatCodeService.selectChatCode(sysChatCode);
        mmap.put("chat", sysChatCodeInfo);
        return prefix + "/edit";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:chat:add")
    @Log(title = "聊天管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysChatCode chat) {
        log.debug("新增博客记录");
        String chatCode = chat.getChatCode();
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("char_").append(chatCode);

        chat.setChatCode(codeBuilder.toString());
        chat.setCreateBy(ShiroUtils.getUserIdStr());
        sysChatCodeService.insertChatCode(chat);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:chat:edit")
    @Log(title = "聊天管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysChatCode chat) {
        chat.setUpdateBy(ShiroUtils.getUserIdStr());
        sysChatCodeService.updateChatCode(chat);
        return toAjax(1);
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:chat:remove")
    @Log(title = "聊天管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@Validated String ids) {
        SysChatCode sysChatCode = new SysChatCode();
        sysChatCode.setChatId(ids);
        sysChatCodeService.deleteChatCodeByIds(sysChatCode);
        return toAjax(1);
    }
}
