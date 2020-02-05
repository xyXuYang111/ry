package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: xuyang
 * @Date: 2020/2/4 12:54
 * @Description:
 */
@Data
@Document(value = "SysSchedule")
public class SysRedisChat extends BaseEntity {

    private static final long serialVersionUID = -5341719428083200109L;

    @Id
    private String chatId;

    private String chatCode;

    private String chatMessage;

    private String sendTime;
}
