package com.ruoyi.system.domain;

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
    private String blogId;

    private String blogTitle;

    private String blogRemark;

    private String blogContent;

    private String blogType;

    private String blogTypeId;

    private String blogUrl;

    private String userId;

    private String ids;
}
