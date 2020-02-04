package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 * 
 * @author ruoyi
 */
@Data
@ToString
@Document(indexName = "login_log_info", type = "login_log_info")
public class SysLogininforElastic extends BaseEntity
{

    private static final long serialVersionUID = -1249300652736649668L;

    /** ID */
    @Id
    @Field(type = FieldType.Text,analyzer = "infoId")
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private String infoId;

    /** 用户账号 */
    @Field(type = FieldType.Text,analyzer = "loginName")
    @Excel(name = "用户账号")
    private String loginName;

    /** 登录状态 0成功 1失败 */
    @Field(type = FieldType.Text,analyzer = "status")
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /** 登录IP地址 */
    @Field(type = FieldType.Text,analyzer = "ipaddr")
    @Excel(name = "登录地址")
    private String ipaddr;

    /** 登录地点 */
    @Field(type = FieldType.Text,analyzer = "loginLocation")
    @Excel(name = "登录地点")
    private String loginLocation;

    /** 浏览器类型 */
    @Field(type = FieldType.Text,analyzer = "browser")
    @Excel(name = "浏览器")
    private String browser;

    /** 操作系统 */
    @Field(type = FieldType.Text,analyzer = "os")
    @Excel(name = "操作系统")
    private String os;

    /** 提示消息 */
    @Field(type = FieldType.Text,analyzer = "msg")
    @Excel(name = "提示消息")
    private String msg;

    /** 访问时间 */
    @Field(type = FieldType.Date,analyzer = "loginTime")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
}