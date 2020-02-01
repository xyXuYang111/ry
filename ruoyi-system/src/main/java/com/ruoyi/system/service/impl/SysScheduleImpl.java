package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysSchedule;
import com.ruoyi.system.mapper.SysScheduleMapper;
import com.ruoyi.system.service.SysScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private static final String schedule_CACHE_KEY = "schedule";

    @Autowired
    private SysScheduleMapper sysScheduleMapper;

    @Override
    @Cacheable(value = schedule_CACHE_KEY, key = "'selectSchedule'+ #sysSchedule.scheduleId")
    public SysSchedule selectSchedule(SysSchedule sysSchedule) {
        return sysScheduleMapper.selectSchedule(sysSchedule);
    }

    @Override
    @Cacheable(value = schedule_CACHE_KEY, key = "'selectScheduleList'+ #sysSchedule.toString()")
    public List<SysSchedule> selectScheduleList(SysSchedule sysSchedule) {
        return sysScheduleMapper.selectScheduleList(sysSchedule);
    }

    @Override
    @Cacheable(value = schedule_CACHE_KEY, key = "'checkScheduleKeyUnique'+ #sysScheduleKey")
    public SysSchedule checkScheduleKeyUnique(String sysScheduleKey) {
        return sysScheduleMapper.checkScheduleKeyUnique(sysScheduleKey);
    }

    @Override
    @CacheEvict(value = schedule_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertSchedule(SysSchedule sysSchedule) {
        sysScheduleMapper.insertSchedule(sysSchedule);
    }

    @Override
    @CacheEvict(value = schedule_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateSchedule(SysSchedule sysSchedule) {
        sysScheduleMapper.updateSchedule(sysSchedule);
    }

    @Override
    @CacheEvict(value = schedule_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteScheduleByIds(SysSchedule sysSchedule) {
        sysScheduleMapper.deleteScheduleByIds(sysSchedule);
    }
}
