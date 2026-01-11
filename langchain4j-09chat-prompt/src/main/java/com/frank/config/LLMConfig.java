package com.frank.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.LawAssistant;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Configuration
public class LLMConfig {

	@Bean(name = "deepseek")
	public ChatModel chatModelGoogle() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("deepseek"))
				.modelName("deepseek-chat")
				.timeout(Duration.ofSeconds(600))
				.logRequests(true)
				.logResponses(true)
				.baseUrl("https://api.deepseek.com/v1").build();
	}

	
    @Bean
    public LawAssistant lawAssistant(ChatModel chatModel) {
        return AiServices.create(LawAssistant.class, chatModel);
    }

}
