package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysMongoDb;
import org.apache.poi.ss.usermodel.Picture;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/29 11:04
 * @Description:
 */
public interface SysMongoDbService {
    //获取一页记录
    List<SysMongoDb> getPageList(int current, int rowCount, String name, String title);
    //获取总数目
    int getSysMongoDbNum();
    //使用主键获取记录
    SysMongoDb getSysMongoDbById(String id);

    void SaveOrUpdateSysMongoDb(SysMongoDb p);

    void deleteSysMongoDb(String id);
    //获取一页搜索结果
    List<SysMongoDb> getSearchResult(int current,int rowCount,String sortId,String search);
    //获取搜索结果总数
    int getSearchResultTotal(String search);
}
