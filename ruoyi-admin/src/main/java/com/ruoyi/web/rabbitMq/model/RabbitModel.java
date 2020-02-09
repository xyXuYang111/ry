package com.ruoyi.web.rabbitMq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: xuyang
 * @Date: 2020/2/8 21:55
 * @Description:
 */
@Data
public class RabbitModel implements Serializable {

    private static final long serialVersionUID = -3006682576234719094L;

    private String key;

    private String value;
}
