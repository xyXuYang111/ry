package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
public class SysDaily extends BaseEntity {

    private static final long serialVersionUID = 1092416681922599174L;
    
    private String dailyId;

    private String dailyName;

    private String dailyContent;

    private String weather;

    private String remark;
}
