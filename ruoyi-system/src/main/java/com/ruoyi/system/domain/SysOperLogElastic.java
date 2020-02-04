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
 * 操作日志记录表 oper_log
 * 
 * @author ruoyi
 */
@Data
@ToString
@Document(indexName = "operate_log_info", type = "operate_log_info")
public class SysOperLogElastic extends BaseEntity
{

    private static final long serialVersionUID = 1873923811082075733L;

    /** 日志主键 */
    @Id
    @Field(type = FieldType.Text,analyzer = "operId")
    @Excel(name = "操作序号", cellType = ColumnType.NUMERIC)
    private String operId;

    /** 操作模块 */
    @Field(type = FieldType.Text, analyzer = "title")
    @Excel(name = "操作模块")
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Field(type = FieldType.Text,analyzer = "businessType")
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    /** 业务类型数组 */
    @Field(type = FieldType.Text,analyzer = "businessTypes")
    private Integer[] businessTypes;

    /** 请求方法 */
    @Field(type = FieldType.Text,analyzer = "method")
    @Excel(name = "请求方法")
    private String method;

    /** 请求方式 */
    @Field(type = FieldType.Text,analyzer = "requestMethod")
    @Excel(name = "请求方式")
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Field(type = FieldType.Text,analyzer = "operatorType")
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    /** 操作人员 */
    @Field(type = FieldType.Text,analyzer = "operName")
    @Excel(name = "操作人员")
    private String operName;

    /** 部门名称 */
    @Field(type = FieldType.Text,analyzer = "deptName")
    @Excel(name = "部门名称")
    private String deptName;

    /** 请求url */
    @Field(type = FieldType.Text,analyzer = "operUrl")
    @Excel(name = "请求地址")
    private String operUrl;

    /** 操作地址 */
    @Field(type = FieldType.Text,analyzer = "operIp")
    @Excel(name = "操作地址")
    private String operIp;

    /** 操作地点 */
    @Field(type = FieldType.Text,analyzer = "operLocation")
    @Excel(name = "操作地点")
    private String operLocation;

    /** 请求参数 */
    @Field(type = FieldType.Text,analyzer = "operParam")
    @Excel(name = "请求参数")
    private String operParam;

    /** 返回参数 */
    @Field(type = FieldType.Text,analyzer = "jsonResult")
    @Excel(name = "返回参数")
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @Field(type = FieldType.Text,analyzer = "status")
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;

    /** 错误消息 */
    @Field(type = FieldType.Text,analyzer = "errorMsg")
    @Excel(name = "错误消息")
    private String errorMsg;

    /** 操作时间 */
    @Field(type = FieldType.Date,analyzer = "operTime")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;
}
