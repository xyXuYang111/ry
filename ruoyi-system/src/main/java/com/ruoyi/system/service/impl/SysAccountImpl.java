package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysAccount;
import com.ruoyi.system.mapper.SysAccountMapper;
import com.ruoyi.system.service.SysAccountService;
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
public class SysAccountImpl implements SysAccountService {

    private static final String ACCOUNT_CACHE_KEY = "account";

    @Autowired
    private SysAccountMapper sysAccountMapper;

    @Override
    @Cacheable(value = ACCOUNT_CACHE_KEY, key = "'selectAccount'+ #sysAccount.accountId")
    public SysAccount selectAccount(SysAccount sysAccount) {
        return sysAccountMapper.selectAccount(sysAccount);
    }

    @Override
    @Cacheable(value = ACCOUNT_CACHE_KEY, key = "'selectAccountList'+ #sysAccount.toString()")
    public List<SysAccount> selectAccountList(SysAccount sysAccount) {
        return sysAccountMapper.selectAccountList(sysAccount);
    }

    @Override
    @Cacheable(value = ACCOUNT_CACHE_KEY, key = "'checkAccountKeyUnique'+ #sysAccountKey")
    public SysAccount checkAccountKeyUnique(String sysAccountKey) {
        return sysAccountMapper.checkAccountKeyUnique(sysAccountKey);
    }

    @Override
    @CacheEvict(value = ACCOUNT_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertAccount(SysAccount sysAccount) {
        sysAccountMapper.insertAccount(sysAccount);
    }

    @Override
    @CacheEvict(value = ACCOUNT_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateAccount(SysAccount sysAccount) {
        sysAccountMapper.updateAccount(sysAccount);
    }

    @Override
    @CacheEvict(value = ACCOUNT_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteAccountByIds(SysAccount sysAccount) {
        sysAccountMapper.deleteAccountByIds(sysAccount);
    }
}
