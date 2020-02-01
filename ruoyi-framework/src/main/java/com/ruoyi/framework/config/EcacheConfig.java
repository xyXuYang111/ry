package com.ruoyi.framework.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @Auther: xuy
 * @Date: 2019/4/7 23:21
 * @Description:
 */
@Data
@Slf4j
@EnableCaching
@Configuration
public class EcacheConfig {

    @Bean(name = "ehCacheManager")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        log.info("ehCache 配置管理器");
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache/ehcache.xml"));
        cacheManagerFactoryBean.setShared (true);
        cacheManagerFactoryBean.setCacheManagerName("cacheManager");
        return cacheManagerFactoryBean;
    }

    @Bean(name = "ehCacheFactoryBean")
    public EhCacheFactoryBean ehCacheFactoryBean(@Qualifier("ehCacheManager") EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        log.info("ehCache 操作对象");
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        //缓存名称
        ehCacheFactoryBean.setCacheName("cacheName");
        ehCacheFactoryBean.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheFactoryBean;
    }

    @Bean(name = "appEhCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(@Qualifier("ehCacheManager") EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        log.info("ehCache 缓存管理");
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
