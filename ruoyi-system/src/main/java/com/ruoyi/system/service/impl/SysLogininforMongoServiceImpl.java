package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.dao.SysLogininforMongoRepository;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.domain.SysMongoDb;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.mapper.SysLogininforMapper;
import com.ruoyi.system.service.ISysLogininforService;
import com.ruoyi.system.service.SysLogininforMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysLogininforMongoServiceImpl implements SysLogininforMongoService
{

    @Autowired
    private SysLogininforMongoRepository sysLogininforMongoRepository;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        sysLogininforMongoRepository.insert(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(int current,int rowCount,SysLogininfor logininfor)
    {
        PageRequest pr = new PageRequest(--current, rowCount, Sort.Direction.DESC, "infoId");
        String ipaddr = logininfor.getIpaddr();
        String loginName = logininfor.getLoginName();
        String status = logininfor.getStatus();
        if(!ipaddr.trim().isEmpty()) {
            Page<SysLogininfor> page = sysLogininforMongoRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else if(!loginName.trim().isEmpty()){
            Page<SysLogininfor> page = sysLogininforMongoRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else if(!status.trim().isEmpty()){
            Page<SysLogininfor> page = sysLogininforMongoRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else{
            Page<SysLogininfor> page=sysLogininforMongoRepository.findAll(pr);
            return page.getContent();
        }
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids)
    {
        String[] idsArray = ids.split(",");
        for(String id : idsArray){
            SysLogininfor sysLogininfor = new SysLogininfor();
            sysLogininfor.setInfoId(id);
            sysLogininforMongoRepository.delete(sysLogininfor);
        }
        return 1;
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        sysLogininforMongoRepository.deleteAll();
    }
}
