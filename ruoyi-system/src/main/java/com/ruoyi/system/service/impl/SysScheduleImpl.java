package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.mapper.SysScheduleMapper;
import com.ruoyi.system.service.SysScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Service
public class SysScheduleImpl implements SysScheduleService {

    @Autowired
    private SysScheduleMapper sysScheduleMapper;

    @Override
    public SysSchedule selectSchedule(SysSchedule sysSchedule) {
        return sysScheduleMapper.selectSchedule(sysSchedule);
    }

    @Override
    public List<SysSchedule> selectScheduleList(SysSchedule sysSchedule) {
        return sysScheduleMapper.selectScheduleList(sysSchedule);
    }

    @Override
    public SysSchedule checkScheduleKeyUnique(String sysScheduleKey) {
        return sysScheduleMapper.checkScheduleKeyUnique(sysScheduleKey);
    }

    @Override
    public void insertSchedule(SysSchedule sysSchedule) {
        sysScheduleMapper.insertSchedule(sysSchedule);
    }

    @Override
    public void updateSchedule(SysSchedule sysSchedule) {
        sysScheduleMapper.updateSchedule(sysSchedule);
    }

    @Override
    public void deleteScheduleByIds(SysSchedule sysSchedule) {
        sysScheduleMapper.deleteScheduleByIds(sysSchedule);
    }
}
