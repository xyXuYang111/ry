package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysEmail;

import java.util.List;

/**
 * 参数配置 业务层
 * 
 * @author ruoyi
 */
public interface SysEmailService
{
    /**
     * 查询参数配置信息
     * 
     * @param sysEmail 参数配置信息
     * @return 参数配置信息
     */
    public SysEmail selectEmail(SysEmail sysEmail);

    /**
     * 查询参数配置列表
     * 
     * @param sysEmail 参数配置信息
     * @return 参数配置集合
     */
    public List<SysEmail> selectEmailList(SysEmail sysEmail);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysEmailKey 参数键名
     * @return 参数配置信息
     */
    public SysEmail checkEmailKeyUnique(String sysEmailKey);

    /**
     * 新增参数配置
     * 
     * @param sysEmail 参数配置信息
     * @return 结果
     */
    public void insertEmail(SysEmail sysEmail);

    /**
     * 修改参数配置
     * 
     * @param sysEmail 参数配置信息
     * @return 结果
     */
    public void updateEmail(SysEmail sysEmail);

    /**
     * 批量删除参数配置
     * 
     * @param sysEmail 需要删除的数据ID
     * @return 结果
     */
    public void deleteEmailByIds(SysEmail sysEmail);

    /**
     * 批量删除参数配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public void sendEmailByIds(String ids);
}