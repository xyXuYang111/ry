package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 部门表 sys_dept
 * 
 * @author ruoyi
 */
@Data
public class SysScheduleType extends BaseEntity {

    private static final long serialVersionUID = -3125786916019576934L;
    /** 部门ID */
    private Long scheduleTypeId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    private String typeName;

    /** 显示顺序 */
    private String orderNum;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父部门名称 */
    private String parentName;
}
