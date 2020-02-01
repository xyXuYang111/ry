package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysFileType;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.mapper.SysFileTypeMapper;
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
public class SysFileTypeImpl implements SysFileTypeService {

    private static final String FILE_TYPE_CACHE_KEY = "fileType";

    @Autowired
    private SysFileTypeMapper sysFileTypeMapper;

    @Override
    @Cacheable(value = FILE_TYPE_CACHE_KEY, key = "'selectFileTypeList'+ #sysFileType.toString()")
    public List<SysFileType> selectFileTypeList(SysFileType sysFileType) {
        return sysFileTypeMapper.selectFileTypeList(sysFileType);
    }

    @Override
    @Cacheable(value = FILE_TYPE_CACHE_KEY, key = "'selectFileTypeTree'+ #sysFileType.toString()")
    public List<Ztree> selectFileTypeTree(SysFileType sysFileType) {
        List<SysFileType> fileTypeList = sysFileTypeMapper.selectFileTypeList(sysFileType);
        List<Ztree> ztrees = initZtree(fileTypeList);
        return ztrees;
    }

    @Override
    @Cacheable(value = FILE_TYPE_CACHE_KEY, key = "'roleFileTypeTreeData'+ #role.toString()")
    public List<Ztree> roleFileTypeTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysFileType> deptList = selectFileTypeList(new SysFileType());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = sysFileTypeMapper.selectRoleFileTypeTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    @Override
    @Cacheable(value = FILE_TYPE_CACHE_KEY, key = "'selectFileTypeCount'+ #parentId")
    public int selectFileTypeCount(Long parentId) {
        SysFileType sysFileType = new SysFileType();
        sysFileType.setParentId(parentId);
        return sysFileTypeMapper.selectFileTypeCount(sysFileType);
    }

    @Override
    @Cacheable(value = FILE_TYPE_CACHE_KEY, key = "'checkFileTypeExistUser'+ #fileTypeId")
    public boolean checkFileTypeExistUser(Long fileTypeId) {
        SysFileType sysFileType = new SysFileType();
        sysFileType.setFileTypeId(fileTypeId);
        return false;
    }

    @Override
    @CacheEvict(value = FILE_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int deleteFileTypeById(Long fileTypeId) {
        sysFileTypeMapper.deleteFileTypeById(fileTypeId);
        return 1;
    }

    @Override
    @CacheEvict(value = FILE_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int insertFileType(SysFileType sysFileType) {
        sysFileTypeMapper.insertFileType(sysFileType);
        return 1;
    }

    @Override
    @CacheEvict(value = FILE_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public int updateFileType(SysFileType sysFileType) {
        sysFileTypeMapper.updateFileType(sysFileType);
        return 1;
    }

    @Override
    @CacheEvict(value = FILE_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public SysFileType selectFileTypeById(Long sysFileTypeId) {
        return sysFileTypeMapper.selectFileTypeById(sysFileTypeId);
    }

    @Override
    @CacheEvict(value = FILE_TYPE_CACHE_KEY, allEntries = true, beforeInvocation = true)
    public SysFileType checkFileTypeNameUnique(SysFileType sysFileType) {
        return sysFileTypeMapper.checkFileTypeNameUnique(sysFileType);
    }

    /**
     * 对象转部门树
     *
     * @param fileTypeList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysFileType> fileTypeList) {
        return initZtree(fileTypeList, null);
    }

    /**
     * 对象转部门树
     *
     * @param fileTypeList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysFileType> fileTypeList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysFileType fileType : fileTypeList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(fileType.getFileTypeId());
            ztree.setpId(fileType.getParentId());
            ztree.setName(fileType.getTypeName());
            ztree.setTitle(fileType.getTypeName());
            if (isCheck) {
                ztree.setChecked(roleDeptList.contains(fileType.getFileTypeId() + fileType.getTypeName()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
