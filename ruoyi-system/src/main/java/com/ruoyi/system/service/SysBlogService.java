package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysBlog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 业务层
 * 
 * @author ruoyi
 */
public interface SysBlogService
{
    /**
     * 查询参数配置信息
     * 
     * @param sysBlog 参数配置信息
     * @return 参数配置信息
     */
    public SysBlog selectBlog(SysBlog sysBlog);

    /**
     * 查询参数配置列表
     * 
     * @param sysBlog 参数配置信息
     * @return 参数配置集合
     */
    public List<SysBlog> selectBlogList(SysBlog sysBlog);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param sysBlogKey 参数键名
     * @return 参数配置信息
     */
    public SysBlog checkBlogKeyUnique(String sysBlogKey);

    /**
     * 新增参数配置
     * 
     * @param sysBlog 参数配置信息
     * @return 结果
     */
    public void insertBlog(SysBlog sysBlog);

    /**
     * 修改参数配置
     * 
     * @param sysBlog 参数配置信息
     * @return 结果
     */
    public void updateBlog(SysBlog sysBlog);

    /**
     * 批量删除参数配置
     * 
     * @param sysBlog 需要删除的数据ID
     * @return 结果
     */
    public void deleteBlogByIds(SysBlog sysBlog);
}