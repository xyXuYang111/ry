package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
@Data
@ToString
@Document(value = "SysAccount")
public class SysAccount extends BaseEntity {

    private static final long serialVersionUID = 8210062396285853697L;

    @Id
    private String accountId;

    private String accountName;

    private String accountPassword;

    private String platform;

    private String userName;
}
