package com.ruoyi.web.task;

import com.ruoyi.web.kafka.user.KafkaSyncRedis;
import com.ruoyi.web.rabbitMq.model.RabbitModel;
import com.ruoyi.web.rabbitMq.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuyang
 * @Date: 2020/2/7 12:55
 * @Description:
 */
@Component
public class RabbitMqSyncTask {

    @Autowired
    private MsgProducer msgProducer;

    public void syncRabbitMqTask(){
        msgProducer.send("ACCOUNT_REDIS", "ACCOUNT_RABBIT_MQ");
        msgProducer.send("BLOG_REDIS", "BLOG_RABBIT_MQ");
        msgProducer.send( "BLOG_TYPE_REDIS", "BLOG_TYPE_RABBIT_MQ");
        msgProducer.send( "CHAT_REDIS", "CHAT_RABBIT_MQ");
        msgProducer.send( "DAILY_REDIS", "DAILY_RABBIT_MQ");
        msgProducer.send("EMAIL_REDIS", "EMAIL_RABBIT_MQ");
        msgProducer.send("FILE_REDIS", "FILE_RABBIT_MQ");
        msgProducer.send("FILE_TYPE_REDIS", "FILE_TYPE_RABBIT_MQ");
        msgProducer.send("SCHEDULE_REDIS", "SCHEDULE_RABBIT_MQ");
    }
}
