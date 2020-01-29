package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysDaily;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysDailyMapper
{
    /**
     * 查询参数配置信息
     * 
     * @param sysDaily 参数配置信息
     * @return 参数配置信息
     */
    public SysDaily selectDaily(SysDaily sysDaily);

    /**
     * 查询参数配置列表
     * 
     * @param sysDaily 参数配置信息
     * @return 参数配置集合
     */
    public List<SysDaily> selectDailyList(SysDaily sysDaily);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysDailyKey 参数键名
     * @return 参数配置信息
     */
    public SysDaily checkDailyKeyUnique(String sysDailyKey);

    /**
     * 新增参数配置
     * 
     * @param sysDaily 参数配置信息
     * @return 结果
     */
    public int insertDaily(SysDaily sysDaily);

    /**
     * 修改参数配置
     * 
     * @param sysDaily 参数配置信息
     * @return 结果
     */
    public int updateDaily(SysDaily sysDaily);

    /**
     * 批量删除参数配置
     * 
     * @param sysDaily 需要删除的数据ID
     * @return 结果
     */
    public int deleteDailyByIds(SysDaily sysDaily);
}