package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Auther: 许洋
 * @Date: 2019/1/6 21:51
 * @Description:
 */
@Data
public class SysEmail extends BaseEntity {

    private static final long serialVersionUID = -8991756927614538137L;

    @JsonProperty(value = "emailId")
    private String emailId;

    @JsonProperty(value = "titleName")
    private String titleName;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "emailType")
    private String emailType;

    @JsonProperty(value = "sendName")
    private String sendName;

    @JsonProperty(value = "sendNumber")
    private String sendNumber;

    @JsonProperty(value = "sendPassword")
    private String sendPassword;

    @JsonProperty(value = "receiveName")
    private String receiveName;

    @JsonProperty(value = "receiveNumber")
    private String receiveNumber;

    @JsonProperty(value = "receivePassword")
    private String receivePassword;
}
