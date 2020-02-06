package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
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
@Document(value = "SysChatCode")
public class SysChatCode extends BaseEntity {

    private static final long serialVersionUID = 1473631573835570024L;

    @Id
    @Excel(name = "回话序号", cellType = Excel.ColumnType.STRING)
    private String chatId;

    @Excel(name = "回话编号", cellType = Excel.ColumnType.STRING)
    private String chatCode;

    @Excel(name = "回话名称", cellType = Excel.ColumnType.STRING)
    private String chatName;

    @Excel(name = "回话内容", cellType = Excel.ColumnType.STRING)
    private String chatMessage;
}
