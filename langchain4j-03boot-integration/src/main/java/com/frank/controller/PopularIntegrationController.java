package com.frank.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PopularIntegrationController
{
    @Resource
    private ChatModel chatModel;

    // http://localhost:9003/lc4j/boot/chat
    @GetMapping(value = "/lc4j/boot/chat")
    public String chat(@RequestParam(value = "prompt", defaultValue = "你是誰?") String prompt)
    {
        return chatModel.chat(prompt);
    }
}
