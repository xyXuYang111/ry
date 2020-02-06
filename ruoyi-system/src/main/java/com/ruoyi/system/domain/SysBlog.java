package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
@Document(value = "SysBlog")
public class SysBlog extends BaseEntity {

    private static final long serialVersionUID = 3665680828986513572L;

    @Id
    @Excel(name = "博客序号", cellType = Excel.ColumnType.STRING)
    private String blogId;

    @Excel(name = "博客标题", cellType = Excel.ColumnType.STRING)
    private String blogTitle;

    @Excel(name = "博客说明", cellType = Excel.ColumnType.STRING)
    private String blogRemark;

    @Excel(name = "博客内容", cellType = Excel.ColumnType.STRING)
    private String blogContent;

    @Excel(name = "博客类型", cellType = Excel.ColumnType.STRING)
    private String blogType;

    private String blogTypeId;

    @Excel(name = "来源地址", cellType = Excel.ColumnType.STRING)
    private String blogUrl;

    private String userId;

    private String ids;
}
