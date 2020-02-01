package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysBlogType;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.mapper.SysBlogTypeMapper;
import com.ruoyi.system.mapper.SysFileTypeMapper;
import com.ruoyi.system.service.SysBlogTypeService;
import com.ruoyi.system.service.SysFileTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 01:00
 * @Description:
 */
@Slf4j
@Service
public class SysBlogTypeImpl implements SysBlogTypeService {

    private static final String BLOG_TYPE_CACHE_KEY = "blogType";

    @Autowired
    private SysBlogTypeMapper sysBlogTypeMapper;

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'selectBlogTypeList'+ #sysBlogType.toString()")
    public List<SysBlogType> selectBlogTypeList(SysBlogType sysBlogType) {
        return sysBlogTypeMapper.selectBlogTypeList(sysBlogType);
    }

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'selectBlogTypeTree'+ #sysBlogType.toString()")
    public List<Ztree> selectBlogTypeTree(SysBlogType sysBlogType) {
        List<SysBlogType> fileTypeList = sysBlogTypeMapper.selectBlogTypeList(sysBlogType);
        List<Ztree> ztrees = initZtree(fileTypeList);
        return ztrees;
    }

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'roleBlogTypeTreeData'+ #role.toString()")
    public List<Ztree> roleBlogTypeTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysBlogType> deptList = selectBlogTypeList(new SysBlogType());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = sysBlogTypeMapper.selectRoleBlogTypeTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'selectBlogTypeCount'+ #parentId")
    public int selectBlogTypeCount(Long parentId) {
        SysBlogType sysBlogType = new SysBlogType();
        sysBlogType.setParentId(parentId);
        return sysBlogTypeMapper.selectBlogTypeCount(sysBlogType);
    }

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'checkBlogTypeExistUser'+ #fileTypeId")
    public boolean checkBlogTypeExistUser(Long fileTypeId) {
        SysBlogType sysBlogType = new SysBlogType();
        sysBlogType.setBlogTypeId(fileTypeId);
        return false;
    }

    @Override
    @CacheEvict(value = BLOG_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int deleteBlogTypeById(Long fileTypeId) {
        sysBlogTypeMapper.deleteBlogTypeById(fileTypeId);
        return 1;
    }

    @Override
    @CacheEvict(value = BLOG_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int insertBlogType(SysBlogType sysBlogType) {
        sysBlogTypeMapper.insertBlogType(sysBlogType);
        return 1;
    }

    @Override
    @CacheEvict(value = BLOG_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int updateBlogType(SysBlogType sysBlogType) {
        sysBlogTypeMapper.updateBlogType(sysBlogType);
        return 1;
    }

    @Override
    public SysBlogType selectBlogTypeById(Long sysBlogTypeId) {
        return sysBlogTypeMapper.selectBlogTypeById(sysBlogTypeId);
    }

    @Override
    @Cacheable(value = BLOG_TYPE_CACHE_KEY, key = "'checkBlogTypeNameUnique'+ #sysBlogType.blogTypeId")
    public SysBlogType checkBlogTypeNameUnique(SysBlogType sysBlogType) {
        return sysBlogTypeMapper.checkBlogTypeNameUnique(sysBlogType);
    }

    /**
     * 对象转部门树
     *
     * @param fileTypeList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysBlogType> fileTypeList) {
        return initZtree(fileTypeList, null);
    }

    /**
     * 对象转部门树
     *
     * @param fileTypeList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysBlogType> fileTypeList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysBlogType fileType : fileTypeList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(fileType.getBlogTypeId());
            ztree.setpId(fileType.getParentId());
            ztree.setName(fileType.getTypeName());
            ztree.setTitle(fileType.getTypeName());
            if (isCheck) {
                ztree.setChecked(roleDeptList.contains(fileType.getBlogTypeId() + fileType.getTypeName()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
