package com.frank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class LLMConfig {

	@Bean
	public ChatModel deepseekMode() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("deepseek-api"))
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com/v1")
			    .build();
	}
}
