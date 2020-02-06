package com.ruoyi.system.repository;

import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.service.SysFileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 21:08
 * @Description:
 */
@Component
public class SysFileTypeRepository {
    
    private static SysFileTypeService sysFileTypeService;

    @Autowired
    public void setSysFileTypeService(SysFileTypeService sysFileTypeService) {
        SysFileTypeRepository.sysFileTypeService = sysFileTypeService;
    }
    
    public static List<SysFileType> selectFileTypeList(SysFileType sysFileType){
        return sysFileTypeService.selectFileTypeList(sysFileType);
    }
}
