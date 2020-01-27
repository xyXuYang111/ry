package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
public class SysBlog extends BaseEntity {

    private static final long serialVersionUID = 7796053323245910719L;

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
