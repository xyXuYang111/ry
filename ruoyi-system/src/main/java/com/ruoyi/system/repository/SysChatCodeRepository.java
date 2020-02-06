package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysChatCode;
import com.ruoyi.system.service.SysChatCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysChatCodeRepository {
    
    private static SysChatCodeService sysChatCodeService;

    @Autowired
    public void setSysChatCodeService(SysChatCodeService sysChatCodeService) {
        SysChatCodeRepository.sysChatCodeService = sysChatCodeService;
    }
    
    public static List<SysChatCode> selectChatCodeList(SysChatCode sysChatCode){
        return sysChatCodeService.selectChatCodeList(sysChatCode);
    }
}
