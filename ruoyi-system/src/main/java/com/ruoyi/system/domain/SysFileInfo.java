package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @Auther: xuyang
 * @Date: 2020/1/23 18:41
 * @Description:
 */
@Data
public class SysFileInfo extends BaseEntity {

    private static final long serialVersionUID = 3882523920150629261L;

    private String fileId;

    private String fileName;

    private String fileTitle;

    private String fileUrl;

    private String fileTypeId;

    private String typeName;

    private String remark;

    private String orderNum;

    @JsonProperty(value = "file")
    private MultipartFile file;
}
