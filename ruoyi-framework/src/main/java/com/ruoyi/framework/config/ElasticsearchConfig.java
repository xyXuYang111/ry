package com.ruoyi.framework.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

/**
 * @Auther: xuy
 * @Date: 2019/4/9 00:19
 * @Description:
 */
@Data
@Slf4j
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ruoyi.system.elasticsearch")
public class ElasticsearchConfig {

    private String EsHost = "127.0.0.1";

    private int EsPort = 9300;

    @Bean(name = "elasticsearchClient")
    public TransportClient elasticsearchClient() throws Exception {
        log.info("连接elasticsearch的Client");
        //创建客户端
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(
                new TransportAddress(InetAddress.getByName(EsHost), EsPort));
        log.info(client.toString());
        return client;
    }

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchTemplate elasticsearchTemplate(@Qualifier("elasticsearchClient") TransportClient client) {
        log.info("配置spring-data-elasticsearch");
        return new ElasticsearchTemplate(client);
    }
}
