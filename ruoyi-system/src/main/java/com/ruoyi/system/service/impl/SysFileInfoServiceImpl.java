package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import com.ruoyi.system.service.SysFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 18:53
 * @Description:
 */
@Slf4j
@Service
public class SysFileInfoServiceImpl implements SysFileInfoService {

    private static final String FILE_INFO_CACHE_KEY = "fileInfo";

    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    @Override
    @Cacheable(value = FILE_INFO_CACHE_KEY, key = "'selectFileInfo'+ #sysFile.fileId")
    public SysFileInfo selectFileInfo(SysFileInfo sysFile) {
        return sysFileInfoMapper.selectFileInfo(sysFile);
    }

    @Override
    @Cacheable(value = FILE_INFO_CACHE_KEY, key = "'selectFileList'+ #sysFile.toString()")
    public List<SysFileInfo> selectFileList(SysFileInfo sysFile) {
        return sysFileInfoMapper.selectFileList(sysFile);
    }

    @Override
    @CacheEvict(value = FILE_INFO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertFile(SysFileInfo sysFile) {
        sysFileInfoMapper.insertFile(sysFile);
    }

    @Override
    @CacheEvict(value = FILE_INFO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateFile(SysFileInfo sysFile) {
        sysFileInfoMapper.updateFile(sysFile);
    }

    @Override
    @CacheEvict(value = FILE_INFO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteFile(SysFileInfo sysFile) {
        sysFileInfoMapper.deleteFile(sysFile);
    }
}
