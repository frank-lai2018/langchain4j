package com.frank.controller;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frank.service.FunctionAssistant;

@RestController
@Slf4j
public class ChatFunctionCallingController
{
    @Resource
    private FunctionAssistant functionAssistant;
    @Resource
    private FunctionAssistant functionAssistant1;

    //  http://localhost:9011/chatfunction/test1
    @GetMapping(value = "/chatfunction/test1")
    public String test1()
    {
        String chat = functionAssistant.chat("開張發票,公司：拉拉拉拉科技有限公司 稅號：lalalalala666 金額：668.12");

        System.out.println(chat);

        return "success : "+ DateUtil.now() + "\t"+chat;
    }
    @GetMapping(value = "/chatfunction/test2")
    public String test2()
    {
    	String chat = functionAssistant1.chat("開張發票,公司：拉拉拉拉科技有限公司 稅號：lalalalala666 金額：668.12");
    	
    	System.out.println(chat);
    	
    	return "success : "+ DateUtil.now() + "\t"+chat;
    }
}
