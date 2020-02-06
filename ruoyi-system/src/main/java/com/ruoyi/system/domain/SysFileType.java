package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 部门表 sys_dept
 * 
 * @author ruoyi
 */
@Data
@Document(value = "SysFileType")
public class SysFileType extends BaseEntity {

    private static final long serialVersionUID = -3125786916019576934L;

    @Id
    @Excel(name = "文件类型编号", cellType = Excel.ColumnType.STRING)
    private Long fileTypeId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    @Excel(name = "文件类型名称", cellType = Excel.ColumnType.STRING)
    private String typeName;

    /** 显示顺序 */
    private String orderNum;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @Excel(name = "父级文件类型名称", cellType = Excel.ColumnType.STRING)
    private String parentName;
}
