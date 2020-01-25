package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysSchedule;

import java.util.List;

/**
 * 参数配置 业务层
 * 
 * @author ruoyi
 */
public interface SysScheduleService
{
    /**
     * 查询参数配置信息
     * 
     * @param sysSchedule 参数配置信息
     * @return 参数配置信息
     */
    public SysSchedule selectSchedule(SysSchedule sysSchedule);

    /**
     * 查询参数配置列表
     * 
     * @param sysSchedule 参数配置信息
     * @return 参数配置集合
     */
    public List<SysSchedule> selectScheduleList(SysSchedule sysSchedule);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysScheduleKey 参数键名
     * @return 参数配置信息
     */
    public SysSchedule checkScheduleKeyUnique(String sysScheduleKey);

    /**
     * 新增参数配置
     * 
     * @param sysSchedule 参数配置信息
     * @return 结果
     */
    public void insertSchedule(SysSchedule sysSchedule);

    /**
     * 修改参数配置
     * 
     * @param sysSchedule 参数配置信息
     * @return 结果
     */
    public void updateSchedule(SysSchedule sysSchedule);

    /**
     * 批量删除参数配置
     * 
     * @param sysSchedule 需要删除的数据ID
     * @return 结果
     */
    public void deleteScheduleByIds(SysSchedule sysSchedule);
}