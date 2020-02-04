package com.ruoyi.framework.config;

import com.ruoyi.framework.redis.RedisRecevier.MessageReceiver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: xuy
 * @Date: 2019/3/17 21:01
 * @Description:
 */
@Configuration
@Slf4j
@Data
public class RedisConfig {

    private String host = "127.0.0.1";

    private String password = "1234qwer";

    private int port = 6379;

    private int maxWait = -1;

    private int maxIdle = 500;

    private int minIdle = 0;

    private String timeOut = "2000";

    /**
     * spring在多长时间后强制使redis中的session失效,默认是1800.(单位/秒)
     */
    private int maxInactiveIntervalInSeconds = 1800;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        log.info("配置连接池");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        log.info("连接redis");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory ){
        log.info("配置spring-data-redis的redisTemplate");
        RedisTemplate stringRedisTemplate = new RedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean(name = "redisMessageListener1")
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory,
            @Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter){
        log.info("redis服务监听者---redisMessageListener1");
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory);
        //匹配所有频道
        redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic("*"));
        return redisMessageListenerContainer;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param receiver
     * @return
     */
    @Bean(name = "listenerAdapter")
    public MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Primary
    @Bean
    public RedisOperationsSessionRepository sessionRepository(@Qualifier("redisTemplate") RedisTemplate<Object, Object> sessionRedisTemplate) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        sessionRepository.setRedisFlushMode(RedisFlushMode.IMMEDIATE);
        sessionRepository.setRedisKeyNamespace("spring-session");
        return sessionRepository;
    }
}
