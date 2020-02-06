package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.service.SysScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysScheduleRepository {
    
    private static SysScheduleService sysScheduleService;

    @Autowired
    public void setSysScheduleService(SysScheduleService sysScheduleService) {
        SysScheduleRepository.sysScheduleService = sysScheduleService;
    }
    
    public static List<SysSchedule> selectScheduleList(SysSchedule sysSchedule){
        return sysScheduleService.selectScheduleList(sysSchedule);
    }
}
