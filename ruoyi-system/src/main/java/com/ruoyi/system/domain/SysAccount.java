package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
@ToString
public class SysAccount extends BaseEntity {

    private static final long serialVersionUID = 1092416681922599174L;
    
    private String accountId;

    private String accountName;

    private String accountPassword;

    private String platform;

    private String userName;

    private String remark;
}
