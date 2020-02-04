package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysChatCode;
import com.ruoyi.system.mapper.SysChatCodeMapper;
import com.ruoyi.system.service.SysChatCodeService;
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
public class SysChatCodeImpl implements SysChatCodeService {

    private static final String CHAT_CODE_CACHE_KEY = "chatCode";

    @Autowired
    private SysChatCodeMapper sysChatCodeMapper;

    @Override
    @Cacheable(value = CHAT_CODE_CACHE_KEY, key = "'selectChatCode'+ #sysChatCode.chatCodeId")
    public SysChatCode selectChatCode(SysChatCode sysChatCode) {
        return sysChatCodeMapper.selectChatCode(sysChatCode);
    }

    @Override
    @Cacheable(value = CHAT_CODE_CACHE_KEY, key = "'selectChatCodeList'+ #sysChatCode.toString()")
    public List<SysChatCode> selectChatCodeList(SysChatCode sysChatCode) {
        return sysChatCodeMapper.selectChatCodeList(sysChatCode);
    }

    @Override
    @Cacheable(value = CHAT_CODE_CACHE_KEY, key = "'checkChatCodeKeyUnique'+ #sysChatCodeKey")
    public SysChatCode checkChatCodeKeyUnique(String sysChatCodeKey) {
        return sysChatCodeMapper.checkChatCodeKeyUnique(sysChatCodeKey);
    }

    @Override
    @CacheEvict(value = CHAT_CODE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertChatCode(SysChatCode sysChatCode) {
        sysChatCodeMapper.insertChatCode(sysChatCode);
    }

    @Override
    @CacheEvict(value = CHAT_CODE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateChatCode(SysChatCode sysChatCode) {
        sysChatCodeMapper.updateChatCode(sysChatCode);
    }

    @Override
    @CacheEvict(value = CHAT_CODE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteChatCodeByIds(SysChatCode sysChatCode) {
        sysChatCodeMapper.deleteChatCodeByIds(sysChatCode);
    }
}
