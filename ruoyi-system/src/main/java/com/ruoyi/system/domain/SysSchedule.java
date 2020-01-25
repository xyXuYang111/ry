package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Auther: xuyang
 * @Date: 2020/1/22 22:52
 * @Description:
 */
@Data
public class SysSchedule extends BaseEntity {

    private static final long serialVersionUID = -5290022225331387376L;

    private String scheduleId;

    private String scheduleName;

    private String scheduleDesc;

    private String scheduleType;

    private String scheduleStatus;

    private String hasMessage;

    private String hasValid;
}
