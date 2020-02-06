package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysAccountRepository {

    private static SysAccountService sysAccountService;

    @Autowired
    public void setSysAccountService(SysAccountService sysAccountService) {
        SysAccountRepository.sysAccountService = sysAccountService;
    }

    public static List<SysAccount> selectAccountList(SysAccount sysAccount){
        return sysAccountService.selectAccountList(sysAccount);
    }
}
