package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysOperLogElastic;
import com.ruoyi.system.elasticsearch.SysOperLogElasticRepository;
import com.ruoyi.system.service.SysOperLogElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysOperLogElasticServiceImpl implements SysOperLogElasticService
{
    private static final String OPER_LOG_ELASTIC_CACHE_KEY = "operLogElastic";

    @Autowired
    private SysOperLogElasticRepository sysOperLogElasticRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @CacheEvict(value = OPER_LOG_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertOperlog(SysOperLogElastic operLog) {
        sysOperLogElasticRepository.save(operLog);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    @Cacheable(value = OPER_LOG_ELASTIC_CACHE_KEY, key = "'selectOperLogList'+ #logininfor.toString()")
    public List<SysOperLogElastic> selectOperLogList(int current,int rowCount,SysOperLogElastic logininfor)
    {
        //分页
        Sort sort = new Sort(Sort.Direction.DESC, "operId");
        PageRequest pr = new PageRequest(--current, rowCount, sort);
        String title = logininfor.getTitle();
        String operName = logininfor.getOperName();
        if(!title.trim().isEmpty()) {
            Page<SysOperLogElastic> page = sysOperLogElasticRepository.findSysOperLogElasticElasticByTitleContaining(title, pr);
            return page.getContent();
        }else if(!operName.trim().isEmpty()){
            Page<SysOperLogElastic> page = sysOperLogElasticRepository.findSysOperLogElasticElasticByTitleContaining(title, pr);
            return page.getContent();
        }else{
            Page<SysOperLogElastic> page=sysOperLogElasticRepository.findAll(pr);
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
    @CacheEvict(value = OPER_LOG_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int deleteOperLogByIds(String ids)
    {
        String[] idsArray = ids.split(",");
        for(String id : idsArray){
            SysOperLogElastic sysOperLog = new SysOperLogElastic();
            sysOperLog.setOperId(id);
            sysOperLogElasticRepository.delete(sysOperLog);
        }
        return 1;
    }

    @Override
    @Cacheable(value = OPER_LOG_ELASTIC_CACHE_KEY, key = "'selectOperLogById'+ #sysOperLog.operId")
    public SysOperLogElastic selectOperLogById(SysOperLogElastic sysOperLog) {
        String operId = sysOperLog.getOperId();
        return sysOperLogElasticRepository.findSysOperLogElasticElasticByOperId(operId);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    @CacheEvict(value = OPER_LOG_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void cleanOperLog()
    {
        sysOperLogElasticRepository.deleteAll();
    }
}
