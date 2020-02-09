package com.ruoyi.web.rabbitMq.producer;

import com.alibaba.fastjson.JSON;
import com.ruoyi.web.rabbitMq.config.RabbitConfig;
import com.ruoyi.web.rabbitMq.model.RabbitModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Auther: xuyang
 * @Date: 2020/2/8 21:50
 * @Description:
 */
@Slf4j
@Configuration
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    //发送消息
    public void send(String key, String message) {
        RabbitModel rabbitModel = new RabbitModel();
        rabbitModel.setKey(key);
        rabbitModel.setValue(message);

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        String content = JSON.toJSONString(rabbitModel);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, content, correlationId);
    }
    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(" 回调id:" + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败:" + cause);
        }
    }
}

