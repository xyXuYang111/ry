package com.ruoyi.web.rabbitMq.receiver;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.redis.redisRepository.RedisRepository;
import com.ruoyi.web.rabbitMq.config.RabbitConfig;
import com.ruoyi.web.rabbitMq.model.RabbitModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: xuyang
 * @Date: 2020/2/8 21:52
 * @Description:
 */
@Slf4j
@Component
@Configuration
public class MsgReceiver {

    @RabbitListener(queues = RabbitConfig.QUEUE_A)
    public void process(String content) {
        log.info("接收处理队列A当中的消息： " + content);

        RabbitModel rabbitModel = JSON.parseObject(content, RabbitModel.class);
        List<String> stringList = RedisRepository.getObjectList(rabbitModel.getKey(), 0, -1);
        if(stringList.size() > 0){
            RedisRepository.delete(rabbitModel.getValue());
            for(String str : stringList){
                RedisRepository.leftPush(rabbitModel.getValue(), str);
            }
        }
    }

}
