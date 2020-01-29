package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysEmail;
import com.ruoyi.system.email.EmailQq;
import com.ruoyi.system.email.EmailSend163;
import com.ruoyi.system.mapper.SysEmailMapper;
import com.ruoyi.system.service.SysEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:57
 * @Description:
 */
@Service
public class SysEmailImpl implements SysEmailService {

    @Autowired
    private SysEmailMapper sysEmailMapper;

    @Override
    public SysEmail selectEmail(SysEmail sysEmail) {
        return sysEmailMapper.selectEmail(sysEmail);
    }

    @Override
    public List<SysEmail> selectEmailList(SysEmail sysEmail) {
        return sysEmailMapper.selectEmailList(sysEmail);
    }

    @Override
    public SysEmail checkEmailKeyUnique(String sysEmailKey) {
        return sysEmailMapper.checkEmailKeyUnique(sysEmailKey);
    }

    @Override
    public void insertEmail(SysEmail sysEmail) {
        sysEmailMapper.insertEmail(sysEmail);
    }

    @Override
    public void updateEmail(SysEmail sysEmail) {
        sysEmailMapper.updateEmail(sysEmail);
    }

    @Override
    public void deleteEmailByIds(SysEmail sysEmail) {
        sysEmailMapper.deleteEmailByIds(sysEmail);
    }

    @Override
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
