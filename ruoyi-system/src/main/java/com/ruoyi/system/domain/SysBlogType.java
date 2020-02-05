package com.ruoyi.system.domain;

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
@Document(value = "SysBlogType")
public class SysBlogType extends BaseEntity {

    private static final long serialVersionUID = 6784444575512611237L;

    @Id
    /** 部门ID */
    private Long blogTypeId;

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
