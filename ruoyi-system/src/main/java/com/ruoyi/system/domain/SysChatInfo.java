package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Auther: xuyang
 * @Date: 2020/2/4 12:54
 * @Description:
 */
@Data
@Document(value = "SysChatInfo")
public class SysChatInfo extends BaseEntity {

    private static final long serialVersionUID = -7284242358466316431L;

    @Id
    private String chatInfoId;

    private String chatCode;

    private String chatName;

    private String chatMessage;
}
