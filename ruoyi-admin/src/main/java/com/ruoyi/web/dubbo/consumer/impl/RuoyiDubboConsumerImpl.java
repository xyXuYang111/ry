package com.ruoyi.web.dubbo.consumer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ruoyi.web.dubbo.consumer.RuoyiDubboConsumer;
import com.ruoyi.web.dubbo.provider.RuoyiDubboProvider;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 23:32
 * @Description:
 */
@Component
@Service
public class RuoyiDubboConsumerImpl implements RuoyiDubboConsumer {

    @Reference(version = "${dubbo.application.version}")
    private RuoyiDubboProvider ruoyiDubboProvider;

    @Override
    public String sayHelloWord() {
        return ruoyiDubboProvider.sayHelloWord();
    }
}
