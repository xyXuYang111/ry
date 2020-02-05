package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:52
 * @Description:
 */
@Data
@Document(value = "SysSchedule")
public class SysSchedule extends BaseEntity {

    private static final long serialVersionUID = -5290022225331387376L;

    @Id
    private String scheduleId;

    private String scheduleName;

    private String scheduleDesc;

    private String scheduleType;

    private String scheduleStatus;

    private String hasMessage;

    private String hasValid;
}
