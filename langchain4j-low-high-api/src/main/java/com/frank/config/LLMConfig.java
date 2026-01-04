package com.frank.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.ChatAssistant;

@Configuration
public class LLMConfig
{

    /**
    * @Description: 知識出處，https://api-docs.deepseek.com/zh-cn/
    */
    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek()
    {
        return
                OpenAiChatModel.builder()
                        .apiKey(System.getenv("deepseek-api"))
                        .modelName("deepseek-chat")
                        //.modelName("deepseek-reasoner")
                        .baseUrl("https://api.deepseek.com/v1")
                .build();
    }



    // High-Api https://docs.langchain4j.dev/tutorials/ai-services#simplest-ai-service
    @Bean
    public ChatAssistant chatAssistant(@Qualifier("deepseek") ChatModel chatModelQwen)
    {
        return AiServices.create(ChatAssistant.class, chatModelQwen);
    }
}
