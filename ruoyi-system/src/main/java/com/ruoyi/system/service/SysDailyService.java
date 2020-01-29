package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDaily;

import java.util.List;

/**
 * 参数配置 业务层
 * 
 * @author ruoyi
 */
public interface SysDailyService
{
    /**
     * 查询参数配置信息
     * 
     * @param sysDaily 参数配置信息
     * @return 参数配置信息
     */
    public SysDaily selectDaily(SysDaily sysDaily);

    /**
     * 查询参数配置列表
     * 
     * @param sysDaily 参数配置信息
     * @return 参数配置集合
     */
    public List<SysDaily> selectDailyList(SysDaily sysDaily);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysDailyKey 参数键名
     * @return 参数配置信息
     */
    public SysDaily checkDailyKeyUnique(String sysDailyKey);

    /**
     * 新增参数配置
     * 
     * @param sysDaily 参数配置信息
     * @return 结果
     */
    public void insertDaily(SysDaily sysDaily);

    /**
     * 修改参数配置
     * 
     * @param sysDaily 参数配置信息
     * @return 结果
     */
    public void updateDaily(SysDaily sysDaily);

    /**
     * 批量删除参数配置
     * 
     * @param sysDaily 需要删除的数据ID
     * @return 结果
     */
    public void deleteDailyByIds(SysDaily sysDaily);
}