package com.ruoyi.web.kafka.listener;

import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

/**
 * @Auther: xuy
 * @Date: 2019/4/8 00:58
 * @Description: topics 和send方法有关，第一个属性值就是这个意思
 */
@Slf4j
@Configuration
@EnableKafka
public class KafkConsumerListener {

    @KafkaListener(topics = "text")
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("kafka的value: " + record.value().toString());
        List<String> stringList = RedisRepository.getObjectList(record.key().toString(), 0, -1);
        if(stringList.size() > 0){
            RedisRepository.delete(record.value().toString());
            for(String str : stringList){
                RedisRepository.leftPush(record.value().toString(), str);
            }
        }
    }
}
