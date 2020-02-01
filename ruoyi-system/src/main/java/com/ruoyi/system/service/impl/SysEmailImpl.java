package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.email.EmailQq;
import com.ruoyi.system.email.EmailSend163;
import com.ruoyi.system.mapper.SysEmailMapper;
import com.ruoyi.system.service.SysEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Slf4j
@Service
public class SysEmailImpl implements SysEmailService {

    private static final String EMAIL_CACHE_KEY = "email";

    @Autowired
    private SysEmailMapper sysEmailMapper;

    @Override
    @Cacheable(value = EMAIL_CACHE_KEY, key = "'selectEmail'+ #sysEmail.emailId")
    public SysEmail selectEmail(SysEmail sysEmail) {
        return sysEmailMapper.selectEmail(sysEmail);
    }

    @Override
    @Cacheable(value = EMAIL_CACHE_KEY, key = "'selectEmailList'+ #sysEmail.toString()")
    public List<SysEmail> selectEmailList(SysEmail sysEmail) {
        return sysEmailMapper.selectEmailList(sysEmail);
    }

    @Override
    @Cacheable(value = EMAIL_CACHE_KEY, key = "'selectEmailList'+ #sysEmailKey")
    public SysEmail checkEmailKeyUnique(String sysEmailKey) {
        return sysEmailMapper.checkEmailKeyUnique(sysEmailKey);
    }

    @Override
    @CacheEvict(value = EMAIL_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertEmail(SysEmail sysEmail) {
        sysEmailMapper.insertEmail(sysEmail);
    }

    @Override
    @CacheEvict(value = EMAIL_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void updateEmail(SysEmail sysEmail) {
        sysEmailMapper.updateEmail(sysEmail);
    }

    @Override
    @CacheEvict(value = EMAIL_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void deleteEmailByIds(SysEmail sysEmail) {
        sysEmailMapper.deleteEmailByIds(sysEmail);
    }

    @Override
    @CacheEvict(value = EMAIL_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void sendEmailByIds(String ids) {
        String[] emailId = ids.split(",");
        if(emailId.length > 0){
            for(String emailIdArray : emailId){
                SysEmail sysEmail = new SysEmail();
                sysEmail.setEmailId(emailIdArray);
                SysEmail sysEmailInfo = sysEmailMapper.selectEmail(sysEmail);
                String emailType = sysEmailInfo.getEmailType();
                if(emailType.equals("1")){
                    try {
                        EmailQq.sendEmailQq(sysEmailInfo);
                        sysEmail.setStatus("1");
                    } catch (Exception e) {
                        sysEmail.setStatus("2");
                    }
                }
                if(emailType.equals("2")){
                    try {
                        EmailSend163.sendMail(sysEmailInfo);
                        sysEmail.setStatus("1");
                    } catch (Exception e) {
                        sysEmail.setStatus("2");
                    }
                }
                sysEmailMapper.updateEmail(sysEmail);
            }
        }
    }
}
