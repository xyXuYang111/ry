package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
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
    @Excel(name = "待办序号", cellType = Excel.ColumnType.STRING)
    private String scheduleId;

    @Excel(name = "待办名称", cellType = Excel.ColumnType.STRING)
    private String scheduleName;

    @Excel(name = "待办描述", cellType = Excel.ColumnType.STRING)
    private String scheduleDesc;

    @Excel(name = "待办类型", cellType = Excel.ColumnType.STRING)
    private String scheduleType;

    private String scheduleStatus;

    private String hasMessage;

    private String hasValid;
}
