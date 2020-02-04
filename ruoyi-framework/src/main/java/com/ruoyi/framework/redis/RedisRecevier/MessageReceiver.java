package com.ruoyi.framework.redis.RedisRecevier;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.system.domain.SysOperLogElastic;
import com.ruoyi.system.domain.SysRedisChat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuy
 * @Date: 2019/4/7 00:27
 * @Description:
 */
@Slf4j
@Component
public class MessageReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    //@Override
    public void onMessage(Message message, byte[] bytes) {
        log.debug("我是监听，我成功了");

        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        Object channel = serializer.deserialize(message.getChannel());
        Object body = serializer.deserialize(message.getBody());
        log.debug("主题: " + channel);
        log.debug("消息内容: " + String.valueOf(body));

        SysRedisChat redisChat = JSON.parseObject(String.valueOf(body), SysRedisChat.class);
        String chatCode = redisChat.getChatCode();
        //记录到redis库中
        redisService.leftPush(chatCode, message);
    }

    public void receiveMessage(String message){
        log.debug("自定义监听");

        SysRedisChat redisChat = JSON.parseObject(String.valueOf(message), SysRedisChat.class);
        String chatCode = redisChat.getChatCode();
        //记录到redis库中
        redisService.leftPush(chatCode, message);
    }
}
