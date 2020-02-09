package com.ruoyi.web.kafka.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuyang
 * @Date: 2020/2/7 12:55
 * @Description:
 */
@Component
public class KafkaSyncRedis {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void syncKafkaTask(){

        kafkaTemplate.send("text", "ACCOUNT_REDIS", "ACCOUNT_KAFKA");
        kafkaTemplate.send("text", "BLOG_REDIS", "BLOG_KAFKA");
        kafkaTemplate.send("text", "BLOG_TYPE_REDIS", "BLOG_TYPE_KAFKA");
        kafkaTemplate.send("text", "CHAT_REDIS", "CHAT_KAFKA");
        kafkaTemplate.send("text", "DAILY_REDIS", "DAILY_KAFKA");
        kafkaTemplate.send("text", "EMAIL_REDIS", "EMAIL_KAFKA");
        kafkaTemplate.send("text", "FILE_REDIS", "FILE_KAFKA");
        kafkaTemplate.send("text", "FILE_TYPE_REDIS", "FILE_TYPE_KAFKA");
        kafkaTemplate.send("text", "SCHEDULE_REDIS", "SCHEDULE_KAFKA");
    }
}
