package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 部门表 sys_dept
 * 
 * @author ruoyi
 */
@Data
@Document(value = "SysScheduleType")
public class SysScheduleType extends BaseEntity {

    private static final long serialVersionUID = -3125786916019576934L;

    @Id
    @Excel(name = "待办类型编号", cellType = Excel.ColumnType.STRING)
    private Long scheduleTypeId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    @Excel(name = "待办类型名称", cellType = Excel.ColumnType.STRING)
    private String typeName;

    /** 显示顺序 */
    private String orderNum;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @Excel(name = "父级待办类型名称", cellType = Excel.ColumnType.STRING)
    private String parentName;
}
