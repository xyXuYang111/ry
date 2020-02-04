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
public class SysChatCode extends BaseEntity {

    private static final long serialVersionUID = 1473631573835570024L;

    private String chatId;

    private String chatCode;

    private String chatName;

    private String chatMessage;

    private String remark;
}
