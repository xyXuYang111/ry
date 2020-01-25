package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysRole;

import java.util.List;

/**
 * 部门管理 服务层
 * 
 * @author ruoyi
 */
public interface SysFileTypeService
{
    /**
     * 查询部门管理数据
     * 
     * @param sysFileType 部门信息
     * @return 部门信息集合
     */
    public List<SysFileType> selectFileTypeList(SysFileType sysFileType);

    /**
     * 查询部门管理树
     * 
     * @param sysFileType 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectFileTypeTree(SysFileType sysFileType);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Ztree> roleFileTypeTreeData(SysRole role);

    /**
     * 查询部门人数
     * 
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectFileTypeCount(Long parentId);

    /**
     * 查询部门是否存在用户
     * 
     * @param fileTypeId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkFileTypeExistUser(Long fileTypeId);

    /**
     * 删除部门管理信息
     * 
     * @param fileTypeId 部门ID
     * @return 结果
     */
    public int deleteFileTypeById(Long fileTypeId);

    /**
     * 新增保存部门信息
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public int insertFileType(SysFileType sysFileType);

    /**
     * 修改保存部门信息
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public int updateFileType(SysFileType sysFileType);

    /**
     * 根据部门ID查询信息
     * 
     * @param sysFileTypeId 部门ID
     * @return 部门信息
     */
    public SysFileType selectFileTypeById(Long sysFileTypeId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public SysFileType checkFileTypeNameUnique(SysFileType sysFileType);
}
