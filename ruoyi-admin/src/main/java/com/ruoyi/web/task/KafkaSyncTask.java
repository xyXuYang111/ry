package com.ruoyi.web.task;

import com.ruoyi.web.kafka.user.KafkaSyncRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuyang
 * @Date: 2020/2/7 12:55
 * @Description:
 */
@Component
public class KafkaSyncTask {

    @Autowired
    private KafkaSyncRedis kafkaSyncRedis;

    public void syncKafkaTask(){
        kafkaSyncRedis.syncKafkaTask();
    }
}
