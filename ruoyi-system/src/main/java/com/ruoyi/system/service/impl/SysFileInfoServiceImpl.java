package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import com.ruoyi.system.service.SysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 18:53
 * @Description:
 */
@Service
public class SysFileInfoServiceImpl implements SysFileInfoService {

    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    @Override
    public SysFileInfo selectFileInfo(SysFileInfo sysFile) {
        return sysFileInfoMapper.selectFileInfo(sysFile);
    }

    @Override
    public List<SysFileInfo> selectFileList(SysFileInfo sysFile) {
        return sysFileInfoMapper.selectFileList(sysFile);
    }

    @Override
    public void insertFile(SysFileInfo sysFile) {
        sysFileInfoMapper.insertFile(sysFile);
    }

    @Override
    public void updateFile(SysFileInfo sysFile) {
        sysFileInfoMapper.updateFile(sysFile);
    }

    @Override
    public void deleteFile(SysFileInfo sysFile) {
        sysFileInfoMapper.deleteFile(sysFile);
    }
}
