package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.service.SysDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysDailyRepository {
    
    private static SysDailyService sysDailyService;

    @Autowired
    public void setSysDailyService(SysDailyService sysDailyService) {
        SysDailyRepository.sysDailyService = sysDailyService;
    }
    
    public static List<SysDaily> selectDailyList(SysDaily sysDaily){
        return sysDailyService.selectDailyList(sysDaily);
    }
}
