package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysBlogType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysBlogTypeMapper
{
    /**
     * 查询部门人数
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public int selectBlogTypeCount(SysBlogType sysBlogType);

    /**
     * 查询部门是否存在用户
     * 
     * @param blogTypeId 部门ID
     * @return 结果
     */
    public int checkBlogTypeExistUser(Long blogTypeId);

    /**
     * 查询部门管理数据
     * 
     * @param sysBlogType 部门信息
     * @return 部门信息集合
     */
    public List<SysBlogType> selectBlogTypeList(SysBlogType sysBlogType);

    /**
     * 删除部门管理信息
     * 
     * @param sysBlogTypeId 部门ID
     * @return 结果
     */
    public int deleteBlogTypeById(Long sysBlogTypeId);

    /**
     * 新增部门信息
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public int insertBlogType(SysBlogType sysBlogType);

    /**
     * 修改部门信息
     * 
     * @param sysBlogType 部门信息
     * @return 结果
     */
    public int updateBlogType(SysBlogType sysBlogType);

    /**
     * 修改子元素关系
     * 
     * @param sysBlogTypes 子元素
     * @return 结果
     */
    public int updateBlogTypeChildren(@Param("sysBlogTypes") List<SysBlogType> sysBlogTypes);

    /**
     * 根据部门ID查询信息
     * 
     * @param sysBlogTypeId 部门ID
     * @return 部门信息
     */
    public SysBlogType selectBlogTypeById(Long sysBlogTypeId);

    /**
     * 校验部门名称是否唯一
     * @return 结果
     */
    public SysBlogType checkBlogTypeNameUnique(SysBlogType sysBlogType);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleBlogTypeTree(Long roleId);

    /**
     * 修改所在部门的父级部门状态
     * 
     * @param sysBlogType 部门
     */
    public void updateBlogTypeStatus(SysBlogType sysBlogType);

    /**
     * 根据ID查询所有子部门
     * @param sysBlogTypeId 部门ID
     * @return 部门列表
     */
    public List<SysBlogType> selectChildrenBlogTypeById(Long sysBlogTypeId);
}
