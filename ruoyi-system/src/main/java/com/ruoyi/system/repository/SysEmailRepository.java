package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.service.SysEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysEmailRepository {
    
    private static SysEmailService sysEmailService;

    @Autowired
    public void setSysEmailService(SysEmailService sysEmailService) {
        SysEmailRepository.sysEmailService = sysEmailService;
    }
    
    public static List<SysEmail> selectEmailList(SysEmail sysEmail){
        return sysEmailService.selectEmailList(sysEmail);
    }
}
