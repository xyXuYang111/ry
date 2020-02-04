package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: xuyang
 * @Date: 2020/2/4 12:54
 * @Description:
 */
@Data
public class SysChatInfo extends BaseEntity {

    private static final long serialVersionUID = -7284242358466316431L;

    private String chatInfoId;

    private String chatCode;

    private String chatName;

    private String chatMessage;

    private String remark;
}
