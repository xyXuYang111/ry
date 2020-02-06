package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
@Document(value = "SysDaily")
public class SysDaily extends BaseEntity {

    private static final long serialVersionUID = 1092416681922599174L;

    @Id
    @Excel(name = "日志序号", cellType = Excel.ColumnType.STRING)
    private String dailyId;

    @Excel(name = "日志名称", cellType = Excel.ColumnType.STRING)
    private String dailyName;

    @Excel(name = "日志内容", cellType = Excel.ColumnType.STRING)
    private String dailyContent;

    @Excel(name = "天气", cellType = Excel.ColumnType.STRING)
    private String weather;
}
