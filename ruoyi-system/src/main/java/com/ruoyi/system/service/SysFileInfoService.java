package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysFileInfo;

import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 18:52
 * @Description:
 */
public interface SysFileInfoService {

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysFile 用户信息
     * @return 用户信息集合信息
     */
    public SysFileInfo selectFileInfo(SysFileInfo sysFile);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysFile 用户信息
     * @return 用户信息集合信息
     */
    public List<SysFileInfo> selectFileList(SysFileInfo sysFile);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysFile 用户信息
     * @return 用户信息集合信息
     */
    public void insertFile(SysFileInfo sysFile);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysFile 用户信息
     * @return 用户信息集合信息
     */
    public void updateFile(SysFileInfo sysFile);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysFile 用户信息
     * @return 用户信息集合信息
     */
    public void deleteFile(SysFileInfo sysFile);
}
