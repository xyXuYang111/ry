package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.mapper.SysDailyMapper;
import com.ruoyi.system.service.SysDailyService;
import com.ruoyi.system.service.SysDailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Slf4j
@Service
public class SysDailyImpl implements SysDailyService {

    private static final String DAILY_CACHE_KEY = "daily";

    @Autowired
    private SysDailyMapper sysDailyMapper;

    @Override
    @Cacheable(value = DAILY_CACHE_KEY, key = "'selectDaily'+ #sysDaily.dailyId")
    public SysDaily selectDaily(SysDaily sysDaily) {
        return sysDailyMapper.selectDaily(sysDaily);
    }

    @Override
    @Cacheable(value = DAILY_CACHE_KEY, key = "'selectDailyList'+ #sysDaily.toString()")
    public List<SysDaily> selectDailyList(SysDaily sysDaily) {
        return sysDailyMapper.selectDailyList(sysDaily);
    }

    @Override
    @Cacheable(value = DAILY_CACHE_KEY, key = "'checkDailyKeyUnique'+ #sysDailyKey")
    public SysDaily checkDailyKeyUnique(String sysDailyKey) {
        return sysDailyMapper.checkDailyKeyUnique(sysDailyKey);
    }

    @Override
    @CacheEvict(value = DAILY_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertDaily(SysDaily sysDaily) {
        sysDailyMapper.insertDaily(sysDaily);
    }

    @Override
    @CacheEvict(value = DAILY_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateDaily(SysDaily sysDaily) {
        sysDailyMapper.updateDaily(sysDaily);
    }

    @Override
    @CacheEvict(value = DAILY_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteDailyByIds(SysDaily sysDaily) {
        sysDailyMapper.deleteDailyByIds(sysDaily);
    }
}
