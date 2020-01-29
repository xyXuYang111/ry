package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysDaily;
import com.ruoyi.system.mapper.SysDailyMapper;
import com.ruoyi.system.service.SysDailyService;
import com.ruoyi.system.service.SysDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Service
public class SysDailyImpl implements SysDailyService {

    @Autowired
    private SysDailyMapper sysDailyMapper;

    @Override
    public SysDaily selectDaily(SysDaily sysDaily) {
        return sysDailyMapper.selectDaily(sysDaily);
    }

    @Override
    public List<SysDaily> selectDailyList(SysDaily sysDaily) {
        return sysDailyMapper.selectDailyList(sysDaily);
    }

    @Override
    public SysDaily checkDailyKeyUnique(String sysDailyKey) {
        return sysDailyMapper.checkDailyKeyUnique(sysDailyKey);
    }

    @Override
    public void insertDaily(SysDaily sysDaily) {
        sysDailyMapper.insertDaily(sysDaily);
    }

    @Override
    public void updateDaily(SysDaily sysDaily) {
        sysDailyMapper.updateDaily(sysDaily);
    }

    @Override
    public void deleteDailyByIds(SysDaily sysDaily) {
        sysDailyMapper.deleteDailyByIds(sysDaily);
    }
}
