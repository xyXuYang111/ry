package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysEmail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysEmailMapper
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
    public int insertEmail(SysEmail sysEmail);

    /**
     * 修改参数配置
     * 
     * @param sysEmail 参数配置信息
     * @return 结果
     */
    public int updateEmail(SysEmail sysEmail);

    /**
     * 批量删除参数配置
     * 
     * @param sysEmail 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmailByIds(SysEmail sysEmail);
}