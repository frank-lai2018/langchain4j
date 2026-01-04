package com.frank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class LLMConfig {

	/**
	 * 使用的https://openrouter.ai/qwen/qwen3-coder:free/api
	 * 	 * */
	@Bean
	public ChatModel chatModelQwen() {
		System.out.println("66666666666666---" + System.getenv("OPENROUTER"));
		System.out.println("66666666666666---deepseek" + System.getenv("DEEPSEEK"));
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("qwen/qwen3-coder:free")
//				.modelName("qwen/qwen-plus")
				.baseUrl("https://openrouter.ai/api/v1").build();
	}
}
