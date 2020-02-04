package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Auther: xuyang
 * @Date: 2020/2/4 12:54
 * @Description:
 */
@Data
public class SysRedisChat extends BaseEntity {

    private static final long serialVersionUID = -5341719428083200109L;

    private String chatId;

    private String chatCode;

    private String chatMessage;

    private String remark;

    private String sendTime;
}
