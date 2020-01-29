package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Auther: xuyang
 * @Date: 2020/1/29 10:40
 * @Description:
 */
@Data
@Document("SysMongoDb")
public class SysMongoDb extends BaseEntity {

    private static final long serialVersionUID = 7560053085874865721L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("password")
    private String password;

    @Field("title")
    private String title;

    @Field("size")
    private int size;

    @Field("url")
    private String url;
}
