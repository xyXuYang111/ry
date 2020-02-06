package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
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
    @Excel(name = "账号序号", cellType = Excel.ColumnType.STRING)
    private String accountId;

    @Excel(name = "登录账号", cellType = Excel.ColumnType.STRING)
    private String accountName;

    @Excel(name = "登录密码", cellType = Excel.ColumnType.STRING)
    private String accountPassword;

    @Excel(name = "平台", cellType = Excel.ColumnType.STRING)
    private String platform;

    @Excel(name = "使用人", cellType = Excel.ColumnType.STRING)
    private String userName;
}
