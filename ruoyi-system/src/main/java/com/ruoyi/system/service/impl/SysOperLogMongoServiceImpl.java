package com.ruoyi.system.service.impl;

import com.ruoyi.system.dao.SysOperLogMongoRepository;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.service.SysOperLogMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class SysOperLogMongoServiceImpl implements SysOperLogMongoDbService
{
    private static final String OPER_LOG_MONGO_CACHE_KEY = "operLogMongo";

    @Autowired
    private SysOperLogMongoRepository sysOperLogMongoRepository;

    @Override
    @CacheEvict(value = OPER_LOG_MONGO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertOperlog(SysOperLog operLog) {
        sysOperLogMongoRepository.insert(operLog);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    @Cacheable(value = OPER_LOG_MONGO_CACHE_KEY, key = "'selectOperLogList'+ #logininfor.toString()")
    public List<SysOperLog> selectOperLogList(int current,int rowCount,SysOperLog logininfor)
    {
        PageRequest pr = new PageRequest(--current, rowCount, Sort.Direction.DESC, "operId");
        String title = logininfor.getTitle();
        String operName = logininfor.getOperName();
        Integer businessType = logininfor.getBusinessType();
        if(!title.trim().isEmpty()) {
            Page<SysOperLog> page = sysOperLogMongoRepository.findSysOperLogByTitleContaining(title, pr);
            return page.getContent();
        }else if(!operName.trim().isEmpty()){
            Page<SysOperLog> page = sysOperLogMongoRepository.findSysOperLogByTitleContaining(title, pr);
            return page.getContent();
        }else{
            Page<SysOperLog> page=sysOperLogMongoRepository.findAll(pr);
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
    @CacheEvict(value = OPER_LOG_MONGO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int deleteOperLogByIds(String ids)
    {
        String[] idsArray = ids.split(",");
        for(String id : idsArray){
            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setOperId(id);
            sysOperLogMongoRepository.delete(sysOperLog);
        }
        return 1;
    }

    @Override
    @Cacheable(value = OPER_LOG_MONGO_CACHE_KEY, key = "'selectOperLogById'+ #sysOperLog.operId")
    public SysOperLog selectOperLogById(SysOperLog sysOperLog) {
        String operId = sysOperLog.getOperId();
        return sysOperLogMongoRepository.findSysOperLogByOperId(operId);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    @CacheEvict(value = OPER_LOG_MONGO_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void cleanOperLog()
    {
        sysOperLogMongoRepository.deleteAll();
    }
}
