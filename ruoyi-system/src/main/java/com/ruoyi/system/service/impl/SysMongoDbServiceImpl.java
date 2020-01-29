package com.ruoyi.system.service.impl;

import com.ruoyi.system.dao.SysMongoDbRepository;
import com.ruoyi.system.domain.SysMongoDb;
import com.ruoyi.system.service.SysMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/29 11:14
 * @Description:
 */
@Service
public class SysMongoDbServiceImpl implements SysMongoDbService {

    @Autowired
    private SysMongoDbRepository sysMongoDbRepository;

    @Override
    public List<SysMongoDb> getPageList(int current, int rowCount, String name, String title) {
        if(!name.trim().isEmpty()) {
            List<SysMongoDb> page = sysMongoDbRepository.findByNameAndTitleContaining(name, title);
            return page;
        }else if(!title.trim().isEmpty()){
            List<SysMongoDb> page = sysMongoDbRepository.findByNameAndTitleContaining(name, title);
            return page;
        }else{
            PageRequest pr = new PageRequest(--current, rowCount);
            Page<SysMongoDb> page=sysMongoDbRepository.findAll(pr);
            return page.getContent();
        }
    }

    @Override
    public int getSysMongoDbNum() {
        return (int) sysMongoDbRepository.count();
    }

    @Override
    public SysMongoDb getSysMongoDbById(String id) {
        return sysMongoDbRepository.findSysMongoDbById(id);
    }

    @Override
    public void SaveOrUpdateSysMongoDb(SysMongoDb p) {
        sysMongoDbRepository.save(p);
    }

    @Override
    public void deleteSysMongoDb(String id) {
        SysMongoDb sysMongoDb=sysMongoDbRepository.findSysMongoDbById(id);
        sysMongoDbRepository.delete(sysMongoDb);
    }

    @Override
    public List<SysMongoDb> getSearchResult(int current, int rowCount, String sortId, String search) {
        PageRequest pr;
        if("asc".equals(sortId))
            pr =new PageRequest(--current, rowCount, Sort.Direction.ASC,"id");
        else if("desc".equals(sortId))
            pr =new PageRequest(--current, rowCount, Sort.Direction.DESC,"id");
        else
            pr =new PageRequest(--current, rowCount);
        Page<SysMongoDb> page=sysMongoDbRepository.findByNameContaining(search, pr);
        return page.getContent();
    }

    @Override
    public int getSearchResultTotal(String search) {
        return 0;
    }
}
