package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.mapper.SysAccountMapper;
import com.ruoyi.system.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Service
public class SysAccountImpl implements SysAccountService {

    @Autowired
    private SysAccountMapper sysAccountMapper;

    @Override
    public SysAccount selectAccount(SysAccount sysAccount) {
        return sysAccountMapper.selectAccount(sysAccount);
    }

    @Override
    public List<SysAccount> selectAccountList(SysAccount sysAccount) {
        return sysAccountMapper.selectAccountList(sysAccount);
    }

    @Override
    public SysAccount checkAccountKeyUnique(String sysAccountKey) {
        return sysAccountMapper.checkAccountKeyUnique(sysAccountKey);
    }

    @Override
    public void insertAccount(SysAccount sysAccount) {
        sysAccountMapper.insertAccount(sysAccount);
    }

    @Override
    public void updateAccount(SysAccount sysAccount) {
        sysAccountMapper.updateAccount(sysAccount);
    }

    @Override
    public void deleteAccountByIds(SysAccount sysAccount) {
        sysAccountMapper.deleteAccountByIds(sysAccount);
    }
}
