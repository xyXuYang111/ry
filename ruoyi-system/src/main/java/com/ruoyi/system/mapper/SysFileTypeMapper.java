package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysFileType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysFileTypeMapper
{
    /**
     * 查询部门人数
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public int selectFileTypeCount(SysFileType sysFileType);

    /**
     * 查询部门是否存在用户
     * 
     * @param fileTypeId 部门ID
     * @return 结果
     */
    public int checkFileTypeExistUser(Long fileTypeId);

    /**
     * 查询部门管理数据
     * 
     * @param sysFileType 部门信息
     * @return 部门信息集合
     */
    public List<SysFileType> selectFileTypeList(SysFileType sysFileType);

    /**
     * 删除部门管理信息
     * 
     * @param sysFileTypeId 部门ID
     * @return 结果
     */
    public int deleteFileTypeById(Long sysFileTypeId);

    /**
     * 新增部门信息
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public int insertFileType(SysFileType sysFileType);

    /**
     * 修改部门信息
     * 
     * @param sysFileType 部门信息
     * @return 结果
     */
    public int updateFileType(SysFileType sysFileType);

    /**
     * 修改子元素关系
     * 
     * @param sysFileTypes 子元素
     * @return 结果
     */
    public int updateFileTypeChildren(@Param("sysFileTypes") List<SysFileType> sysFileTypes);

    /**
     * 根据部门ID查询信息
     * 
     * @param sysFileTypeId 部门ID
     * @return 部门信息
     */
    public SysFileType selectFileTypeById(Long sysFileTypeId);

    /**
     * 校验部门名称是否唯一
     * @return 结果
     */
    public SysFileType checkFileTypeNameUnique(SysFileType sysFileType);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleFileTypeTree(Long roleId);

    /**
     * 修改所在部门的父级部门状态
     * 
     * @param sysFileType 部门
     */
    public void updateFileTypeStatus(SysFileType sysFileType);

    /**
     * 根据ID查询所有子部门
     * @param sysFileTypeId 部门ID
     * @return 部门列表
     */
    public List<SysFileType> selectChildrenFileTypeById(Long sysFileTypeId);
}
