package com.frank.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frank.service.ChatAssistant;

@RestController
public class DeclarativeAIServiceController
{
    @Resource
    private ChatAssistant chatAssistantQwen;

    // http://localhost:9003/lc4j/boot/declarative
    @GetMapping(value = "/lc4j/boot/declarative")
    public String declarative(@RequestParam(value = "prompt", defaultValue = "你是誰?") String prompt)
    {
        return chatAssistantQwen.chat(prompt);
    }
}
