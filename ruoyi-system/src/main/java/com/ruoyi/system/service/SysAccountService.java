package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysAccount;

import java.util.List;

/**
 * 参数配置 业务层
 * 
 * @author ruoyi
 */
public interface SysAccountService
{
    /**
     * 查询参数配置信息
     * 
     * @param sysAccount 参数配置信息
     * @return 参数配置信息
     */
    public SysAccount selectAccount(SysAccount sysAccount);

    /**
     * 查询参数配置列表
     * 
     * @param sysAccount 参数配置信息
     * @return 参数配置集合
     */
    public List<SysAccount> selectAccountList(SysAccount sysAccount);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysAccountKey 参数键名
     * @return 参数配置信息
     */
    public SysAccount checkAccountKeyUnique(String sysAccountKey);

    /**
     * 新增参数配置
     * 
     * @param sysAccount 参数配置信息
     * @return 结果
     */
    public void insertAccount(SysAccount sysAccount);

    /**
     * 修改参数配置
     * 
     * @param sysAccount 参数配置信息
     * @return 结果
     */
    public void updateAccount(SysAccount sysAccount);

    /**
     * 批量删除参数配置
     * 
     * @param sysAccount 需要删除的数据ID
     * @return 结果
     */
    public void deleteAccountByIds(SysAccount sysAccount);
}