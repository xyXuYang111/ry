package com.ruoyi.web.task;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.server.SysFile;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 12:28
 * @Description:
 */
@Component
public class MongoSyncTask {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void syncMongoTask(){

        List<SysAccount> sysAccountList = SysAccountRepository.selectAccountList(new SysAccount());
        //所有记录进行存储
        if(sysAccountList.size() > 0){
            for(SysAccount sysAccount : sysAccountList){
                mongoTemplate.remove(sysAccount);
                mongoTemplate.insert(sysAccount);
            }
        }

        List<SysBlog> sysBlogList = SysBlogRepository.selectBlogList(new SysBlog());
        //所有记录进行存储
        if(sysBlogList.size() > 0){
            for(SysBlog sysBlog : sysBlogList){
                mongoTemplate.remove(sysBlog);
                mongoTemplate.insert(sysBlog);
            }
        }

        List<SysBlogType> sysBlogTypeList = SysBlogTypeRepository.selectBlogTypeList(new SysBlogType());
        //所有记录进行存储
        if(sysBlogTypeList.size() > 0){
            for(SysBlogType sysBlogType : sysBlogTypeList){
                mongoTemplate.remove(sysBlogType);
                mongoTemplate.insert(sysBlogType);
            }
        }

        List<SysChatCode> sysChatCodeList = SysChatCodeRepository.selectChatCodeList(new SysChatCode());
        //所有记录进行存储
        if(sysChatCodeList.size() > 0){
            for(SysChatCode sysChatCode : sysChatCodeList){
                mongoTemplate.remove(sysChatCode);
                mongoTemplate.insert(sysChatCode);
            }
        }

        List<SysDaily> sysDailyList = SysDailyRepository.selectDailyList(new SysDaily());
        //所有记录进行存储
        if(sysDailyList.size() > 0){
            for(SysDaily sysDaily : sysDailyList){
                mongoTemplate.remove(sysDaily);
                mongoTemplate.insert(sysDaily);
            }
        }

        List<SysEmail> sysEmailList = SysEmailRepository.selectEmailList(new SysEmail());
        //所有记录进行存储
        if(sysEmailList.size() > 0){
            for(SysEmail sysEmail : sysEmailList){
                mongoTemplate.remove(sysEmail);
                mongoTemplate.insert(sysEmail);
            }
        }

        List<SysFileInfo> sysFileInfoList = SysFileInfoRepository.selectFileList(new SysFileInfo());
        //所有记录进行存储
        if(sysFileInfoList.size() > 0){
            for(SysFileInfo sysFileInfo : sysFileInfoList){
                mongoTemplate.remove(sysFileInfo);
                mongoTemplate.insert(sysFileInfo);
            }
        }

        List<SysFileType> sysFileTypeList = SysFileTypeRepository.selectFileTypeList(new SysFileType());
        //所有记录进行存储
        if(sysFileTypeList.size() > 0){
            for(SysFileType sysFileType : sysFileTypeList){
                mongoTemplate.remove(sysFileType);
                mongoTemplate.insert(sysFileType);
            }
        }

        List<SysSchedule> sysScheduleList = SysScheduleRepository.selectScheduleList(new SysSchedule());
        //所有记录进行存储
        if(sysScheduleList.size() > 0){
            for(SysSchedule sysSchedule : sysScheduleList){
                mongoTemplate.remove(sysSchedule);
                mongoTemplate.insert(sysSchedule);
            }
        }
    }
}
