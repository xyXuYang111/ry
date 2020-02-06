package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: 许洋
 * @Date: 2019/1/6 21:51
 * @Description:
 */
@Data
@Document(value = "SysEmail")
public class SysEmail extends BaseEntity {

    private static final long serialVersionUID = -8991756927614538137L;

    @Id
    @Excel(name = "邮件序号", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "emailId")
    private String emailId;

    @Excel(name = "邮件名称", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "titleName")
    private String titleName;

    @Excel(name = "邮件内容", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "emailType")
    private String emailType;

    @Excel(name = "发送人名称", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "sendName")
    private String sendName;

    @Excel(name = "发送人账号", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "sendNumber")
    private String sendNumber;

    @Excel(name = "发送人密码", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "sendPassword")
    private String sendPassword;

    @Excel(name = "接收人名称", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "receiveName")
    private String receiveName;

    @Excel(name = "接收人账号", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "receiveNumber")
    private String receiveNumber;

    @Excel(name = "接收人密码", cellType = Excel.ColumnType.STRING)
    @JsonProperty(value = "receivePassword")
    private String receivePassword;
}
