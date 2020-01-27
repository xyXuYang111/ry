package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.domain.SysRole;

import java.util.List;

/**
 * 部门管理 服务层
 * 
 * @author ruoyi
 */
public interface SysBlogTypeService
{
    /**
     * 查询部门管理数据
     * 
     * @param sysBlogType 部门信息
     * @return 部门信息集合
     */
    public List<SysBlogType> selectBlogTypeList(SysBlogType sysBlogType);

    /**
     * 查询部门管理树
     * 
     * @param sysBlogType 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectBlogTypeTree(SysBlogType sysBlogType);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Ztree> roleBlogTypeTreeData(SysRole role);

    /**
     * 查询部门人数
     * 
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectBlogTypeCount(Long parentId);

    /**
     * 查询部门是否存在用户
     * 
     * @param fileTypeId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkBlogTypeExistUser(Long fileTypeId);

    /**
     * 删除部门管理信息
     * 
     * @param fileTypeId 部门ID
     * @return 结果
     */
    public int deleteBlogTypeById(Long fileTypeId);

    /**
     * 新增保存部门信息
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public int insertBlogType(SysBlogType sysBlogType);

    /**
     * 修改保存部门信息
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public int updateBlogType(SysBlogType sysBlogType);

    /**
     * 根据部门ID查询信息
     * 
     * @param sysBlogTypeId 部门ID
     * @return 部门信息
     */
    public SysBlogType selectBlogTypeById(Long sysBlogTypeId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public SysBlogType checkBlogTypeNameUnique(SysBlogType sysBlogType);
}
