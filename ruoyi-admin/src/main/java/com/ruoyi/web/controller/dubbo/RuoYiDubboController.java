package com.ruoyi.web.controller.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ruoyi.web.dubbo.consumer.RuoyiDubboConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: xuyang
 * @Date: 2020/2/6 22:29
 * @Description:
 */
@Slf4j
@Controller
public class RuoYiDubboController {

    @Autowired
    private RuoyiDubboConsumer ruoyiDubboConsumer;

    @ResponseBody
    @RequestMapping(name = "sayHelloWord", method = RequestMethod.GET)
    public String sayHelloWord(String name){
        log.debug(name + ruoyiDubboConsumer.sayHelloWord());
        return name + ruoyiDubboConsumer.sayHelloWord();
    }
}
