package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 18:41
 * @Description:
 */
@Data
@Document(value = "SysFileInfo")
public class SysFileInfo extends BaseEntity {

    private static final long serialVersionUID = 3882523920150629261L;

    @Id
    @Excel(name = "文件序号", cellType = Excel.ColumnType.STRING)
    private String fileId;

    @Excel(name = "文件名称", cellType = Excel.ColumnType.STRING)
    private String fileName;

    @Excel(name = "文件标题", cellType = Excel.ColumnType.STRING)
    private String fileTitle;

    @Excel(name = "文件路径", cellType = Excel.ColumnType.STRING)
    private String fileUrl;

    private String typeId;

    @Excel(name = "文件类型", cellType = Excel.ColumnType.STRING)
    private String typeName;

    private String orderNum;

    @JsonProperty(value = "file")
    private MultipartFile file;
}
