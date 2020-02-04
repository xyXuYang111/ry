package com.ruoyi.framework.redis.RedisSender;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.framework.util.DateUtil;
import com.ruoyi.system.domain.SysRedisChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

/**
 * @Auther: xuy
 * @Date: 2019/4/7 00:26
 * @Description:
 */
@Component
public class MessageSender {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendMessage(String message){

        SysRedisChat redisChat = JSON.parseObject(message, SysRedisChat.class);
        redisChat.setSendTime(DateUtil.getNowTime());

        String redisMessage = JSON.toJSONString(redisChat);

        stringRedisTemplate.convertAndSend("chat", redisMessage);
    }
}
