package com.ruoyi.web.kafka.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: xuy
 * @Date: 2019/4/8 00:53
 * @Description:
 */
@Data
@Slf4j
@EnableKafka
@Configuration
public class KafkaProducerConfig {

    private String servers = "127.0.0.1:9092";

    private int retries = 0;

    private int batchSize = 4096;

    private int linger = 1;

    private int bufferMemory = 40960;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean(name = "producerFactory")
    public ProducerFactory<String, String> producerFactory() {
        log.info("消息发送对象配置");
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate(
            @Qualifier("producerFactory") ProducerFactory<String, String> producerFactory) {
        log.info("消息发送对象配置--kafkaTemplate");
        return new KafkaTemplate<>(producerFactory);
    }
}
