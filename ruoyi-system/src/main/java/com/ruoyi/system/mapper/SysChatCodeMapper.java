package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysChatCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysChatCodeMapper
{
    /**
     * 查询参数配置信息
     * 
     * @param sysChatCode 参数配置信息
     * @return 参数配置信息
     */
    public SysChatCode selectChatCode(SysChatCode sysChatCode);

    /**
     * 查询参数配置列表
     * 
     * @param sysChatCode 参数配置信息
     * @return 参数配置集合
     */
    public List<SysChatCode> selectChatCodeList(SysChatCode sysChatCode);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysChatCodeKey 参数键名
     * @return 参数配置信息
     */
    public SysChatCode checkChatCodeKeyUnique(String sysChatCodeKey);

    /**
     * 新增参数配置
     * 
     * @param sysChatCode 参数配置信息
     * @return 结果
     */
    public int insertChatCode(SysChatCode sysChatCode);

    /**
     * 修改参数配置
     * 
     * @param sysChatCode 参数配置信息
     * @return 结果
     */
    public int updateChatCode(SysChatCode sysChatCode);

    /**
     * 批量删除参数配置
     * 
     * @param sysChatCode 需要删除的数据ID
     * @return 结果
     */
    public int deleteChatCodeByIds(SysChatCode sysChatCode);
}