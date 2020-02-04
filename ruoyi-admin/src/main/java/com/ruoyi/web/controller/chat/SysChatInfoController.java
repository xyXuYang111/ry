package com.ruoyi.web.controller.chat;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.redis.RedisSender.MessageSender;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysChatInfo;
import com.ruoyi.system.domain.SysRedisChat;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/4 15:02
 * @Description:
 */
@Controller
@RequestMapping("/system/chatInfo")
@Slf4j
public class SysChatInfoController extends BaseController {

    private String prefix = "chatInfo";

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageSender messageSender;

    @RequiresPermissions("system:chatInfo:list")
    @GetMapping()
    public String operlog(SysChatInfo sysChatCode, Model model, HttpSession session) {
        log.debug("登录主界面");
        session.setAttribute("chat_code", sysChatCode.getChatCode());
        model.addAttribute("sysChatCode", sysChatCode);
        return prefix + "/chatInfo";
    }

    @RequiresPermissions("system:chatInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HttpSession session)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        if(pageNum == null){
            pageNum = 1;
        }
        Integer pageSize = pageDomain.getPageSize();
        if(pageSize == null){
            pageSize = 10;
        }
        String chatCode = (String) session.getAttribute("chat_code");
        List<String> list = redisService.getObjectList(chatCode, pageNum, pageSize);
        List<SysChatInfo> sysChatInfoList = new ArrayList<>();
        if(list.size() > 0){
            for(String str : list){
                SysChatInfo redisChat = JSON.parseObject(str, SysChatInfo.class);
                sysChatInfoList.add(redisChat);
            }
        }
        return getDataTable(sysChatInfoList);
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
     * 新增保存岗位
     */
    @RequiresPermissions("system:chatInfo:add")
    @Log(title = "聊天管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysChatInfo sysChatCode, HttpSession session) {
        log.debug("新增博客记录");
        String chatCode = (String) session.getAttribute("chat_code");
        String remark = sysChatCode.getRemark();
        sysChatCode.setCreateBy(ShiroUtils.getUserIdStr());
        sysChatCode.setChatCode(chatCode);
        sysChatCode.setChatMessage(remark);
        String jsonObject = JSON.toJSONString(sysChatCode);
        messageSender.sendMessage(jsonObject);
        return toAjax(1);
    }
}
