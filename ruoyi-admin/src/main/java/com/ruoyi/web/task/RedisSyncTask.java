package com.ruoyi.web.task;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.repository.*;
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
public class RedisSyncTask {

    public void syncRedisTask(){

        List<SysAccount> sysAccountList = SysAccountRepository.selectAccountList(new SysAccount());
        //所有记录进行存储
        if(sysAccountList.size() > 0){
            RedisRepository.delete("ACCOUNT_REDIS");
            for(SysAccount sysAccount : sysAccountList){
                String str = JSON.toJSONString(sysAccount);
                RedisRepository.leftPush("ACCOUNT_REDIS", str);
            }
        }

        List<SysBlog> sysBlogList = SysBlogRepository.selectBlogList(new SysBlog());
        //所有记录进行存储
        if(sysBlogList.size() > 0){
            RedisRepository.delete("BLOG_REDIS");
            for(SysBlog sysBlog : sysBlogList){
                String str = JSON.toJSONString(sysBlog);
                RedisRepository.leftPush("BLOG_REDIS", str);
            }
        }

        List<SysBlogType> sysBlogTypeList = SysBlogTypeRepository.selectBlogTypeList(new SysBlogType());
        //所有记录进行存储
        if(sysBlogTypeList.size() > 0){
            RedisRepository.delete("BLOG_TYPE_REDIS");
            for(SysBlogType sysBlogType : sysBlogTypeList){
                String str = JSON.toJSONString(sysBlogType);
                RedisRepository.leftPush("BLOG_TYPE_REDIS", str);
            }
        }

        List<SysChatCode> sysChatCodeList = SysChatCodeRepository.selectChatCodeList(new SysChatCode());
        //所有记录进行存储
        if(sysChatCodeList.size() > 0){
            RedisRepository.delete("CHAT_REDIS");
            for(SysChatCode sysChatCode : sysChatCodeList){
                String str = JSON.toJSONString(sysChatCode);
                RedisRepository.leftPush("CHAT_REDIS", str);
            }
        }

        List<SysDaily> sysDailyList = SysDailyRepository.selectDailyList(new SysDaily());
        //所有记录进行存储
        if(sysDailyList.size() > 0){
            RedisRepository.delete("DAILY_REDIS");
            for(SysDaily sysDaily : sysDailyList){
                String str = JSON.toJSONString(sysDaily);
                RedisRepository.leftPush("DAILY_REDIS", str);
            }
        }

        List<SysEmail> sysEmailList = SysEmailRepository.selectEmailList(new SysEmail());
        //所有记录进行存储
        if(sysEmailList.size() > 0){
            RedisRepository.delete("EMAIL_REDIS");
            for(SysEmail sysEmail : sysEmailList){
                String str = JSON.toJSONString(sysEmail);
                RedisRepository.leftPush("EMAIL_REDIS", str);
            }
        }

        List<SysFileInfo> sysFileInfoList = SysFileInfoRepository.selectFileList(new SysFileInfo());
        //所有记录进行存储
        if(sysFileInfoList.size() > 0){
            RedisRepository.delete("FILE_REDIS");
            for(SysFileInfo sysFileInfo : sysFileInfoList){
                String str = JSON.toJSONString(sysFileInfo);
                RedisRepository.leftPush("FILE_REDIS", str);
            }
        }

        List<SysFileType> sysFileTypeList = SysFileTypeRepository.selectFileTypeList(new SysFileType());
        //所有记录进行存储
        if(sysFileTypeList.size() > 0){
            RedisRepository.delete("FILE_TYPE_REDIS");
            for(SysFileType sysFileType : sysFileTypeList){
                String str = JSON.toJSONString(sysFileType);
                RedisRepository.leftPush("FILE_TYPE_REDIS", str);
            }
        }

        List<SysSchedule> sysScheduleList = SysScheduleRepository.selectScheduleList(new SysSchedule());
        //所有记录进行存储
        if(sysScheduleList.size() > 0){
            RedisRepository.delete("SCHEDULE_REDIS");
            for(SysSchedule sysSchedule : sysScheduleList){
                String str = JSON.toJSONString(sysSchedule);
                RedisRepository.leftPush("SCHEDULE_REDIS", str);
            }
        }
    }
}
