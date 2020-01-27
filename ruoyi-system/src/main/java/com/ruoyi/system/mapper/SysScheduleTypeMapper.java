package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysScheduleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysScheduleTypeMapper
{
    /**
     * 查询部门人数
     * 
     * @param sysScheduleType 部门信息
     * @return 结果
     */
    public int selectScheduleTypeCount(SysScheduleType sysScheduleType);

    /**
     * 查询部门是否存在用户
     * 
     * @param blogTypeId 部门ID
     * @return 结果
     */
    public int checkScheduleTypeExistUser(Long blogTypeId);

    /**
     * 查询部门管理数据
     * 
     * @param sysScheduleType 部门信息
     * @return 部门信息集合
     */
    public List<SysScheduleType> selectScheduleTypeList(SysScheduleType sysScheduleType);

    /**
     * 删除部门管理信息
     * 
     * @param sysScheduleTypeId 部门ID
     * @return 结果
     */
    public int deleteScheduleTypeById(Long sysScheduleTypeId);

    /**
     * 新增部门信息
     * 
     * @param sysScheduleType 部门信息
     * @return 结果
     */
    public int insertScheduleType(SysScheduleType sysScheduleType);

    /**
     * 修改部门信息
     * 
     * @param sysScheduleType 部门信息
     * @return 结果
     */
    public int updateScheduleType(SysScheduleType sysScheduleType);

    /**
     * 修改子元素关系
     * 
     * @param sysScheduleTypes 子元素
     * @return 结果
     */
    public int updateScheduleTypeChildren(@Param("sysScheduleTypes") List<SysScheduleType> sysScheduleTypes);

    /**
     * 根据部门ID查询信息
     * 
     * @param sysScheduleTypeId 部门ID
     * @return 部门信息
     */
    public SysScheduleType selectScheduleTypeById(Long sysScheduleTypeId);

    /**
     * 校验部门名称是否唯一
     * @return 结果
     */
    public SysScheduleType checkScheduleTypeNameUnique(SysScheduleType sysScheduleType);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleScheduleTypeTree(Long roleId);

    /**
     * 修改所在部门的父级部门状态
     * 
     * @param sysScheduleType 部门
     */
    public void updateScheduleTypeStatus(SysScheduleType sysScheduleType);

    /**
     * 根据ID查询所有子部门
     * @param sysScheduleTypeId 部门ID
     * @return 部门列表
     */
    public List<SysScheduleType> selectChildrenScheduleTypeById(Long sysScheduleTypeId);
}
