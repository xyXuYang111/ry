package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.service.SysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysFileInfoRepository {
    
    private static SysFileInfoService sysFileInfoService;

    @Autowired
    public void setSysFileInfoService(SysFileInfoService sysFileInfoService) {
        SysFileInfoRepository.sysFileInfoService = sysFileInfoService;
    }
    
    public static List<SysFileInfo> selectFileList(SysFileInfo sysFileInfo){
        return sysFileInfoService.selectFileList(sysFileInfo);
    }
}
