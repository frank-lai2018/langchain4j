package com.frank.controller;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LowApiController
{

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepSeek;


    // http://localhost:9004/lowapi/api02
    /**
     * @Description: Token 用量計算的底層api演示驗證案例
     */
    @GetMapping(value = "/lowapi/api02")
    public String api02(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        ChatResponse chatResponse = chatModelDeepSeek.chat(UserMessage.from(prompt));

        String result = chatResponse.aiMessage().text();
        System.out.println("透過調用大模型返回的結果："+result);

        // Token 用量计算的底层api
        TokenUsage tokenUsage = chatResponse.tokenUsage();

        System.out.println("本是調用消耗的token："+tokenUsage);

        result = result +"\t\n"+tokenUsage;

        return result;
    }

}
