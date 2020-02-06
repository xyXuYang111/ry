package com.ruoyi.web.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ruoyi.web.dubbo.provider.RuoyiDubboProvider;
import org.springframework.stereotype.Component;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 22:26
 * @Description:
 */
@Component
@Service(version = "${dubbo.application.version}")
public class RuoyiDubboProviderImpl implements RuoyiDubboProvider {
    @Override
    public String sayHelloWord() {
        return "世界你好，我许洋的dubbo问世了";
    }
}
