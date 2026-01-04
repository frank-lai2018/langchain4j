package com.frank.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MultiModelController
{
    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;

    // http://localhost:9002/multimodel/qwen
    @GetMapping(value = "/multimodel/qwen")
    public String qwenCall(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        String result = chatModelQwen.chat(prompt);

        System.out.println("透過langchain4j呼叫模型傳回結果：\n"+result);

        return result;
    }

    // http://localhost:9002/multimodel/deepseek
    @GetMapping(value = "/multimodel/deepseek")
    public String deepseekCall(@RequestParam(value = "prompt", defaultValue = "你是誰") String prompt)
    {
        String result = chatModelDeepSeek.chat(prompt);

        System.out.println("透過langchain4j呼叫模型傳回結果：\n"+result);

        return result;
    }
}
