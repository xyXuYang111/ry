package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysLogininforElastic;
import com.ruoyi.system.elasticsearch.SysLogininforElasticRepository;
import com.ruoyi.system.service.SysLogininforElasticService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class SysLogininforElasticServiceImpl implements SysLogininforElasticService
{

    private static final String LOGININ_ELASTIC_CACHE_KEY = "logininElastic";

    @Autowired
    private SysLogininforElasticRepository sysLogininforElasticRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    @CacheEvict(value = LOGININ_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void insertLogininfor(SysLogininforElastic logininfor)
    {
        sysLogininforElasticRepository.save(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    @Cacheable(value = LOGININ_ELASTIC_CACHE_KEY, key = "'selectLogininforList'+ #logininfor.infoId")
    public List<SysLogininforElastic> selectLogininforList(int current,int rowCount,SysLogininforElastic logininfor)
    {
        //分页
        Sort sort = new Sort(Sort.Direction.DESC, "infoId");
        PageRequest pr = new PageRequest(--current, rowCount, Sort.Direction.DESC, "infoId");
        String ipaddr = logininfor.getIpaddr();
        String loginName = logininfor.getLoginName();
        String status = logininfor.getStatus();
        if(!ipaddr.trim().isEmpty()) {
            Page<SysLogininforElastic> page = sysLogininforElasticRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else if(!loginName.trim().isEmpty()){
            Page<SysLogininforElastic> page = sysLogininforElasticRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else if(!status.trim().isEmpty()){
            Page<SysLogininforElastic> page = sysLogininforElasticRepository.findByIpaddrContaining(ipaddr, pr);
            return page.getContent();
        }else{
            Page<SysLogininforElastic> page=sysLogininforElasticRepository.findAll(pr);
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
    @CacheEvict(value = LOGININ_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int deleteLogininforByIds(String ids)
    {
        String[] idsArray = ids.split(",");
        for(String id : idsArray){
            SysLogininforElastic sysLogininfor = new SysLogininforElastic();
            sysLogininfor.setInfoId(id);
            sysLogininforElasticRepository.delete(sysLogininfor);
        }
        return 1;
    }

    /**
     * 清空系统登录日志
     */
    @Override
    @CacheEvict(value = LOGININ_ELASTIC_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public void cleanLogininfor()
    {
        sysLogininforElasticRepository.deleteAll();
    }
}
