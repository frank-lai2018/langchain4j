package com.frank.config;

import dev.langchain4j.model.chat.ChatModel;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*

* @Description: 知識來源 https://docs.langchain4j.dev/get-started

*/

@Configuration

public class LLMConfig {

	//調用openrouter的Qwen模型
	@Bean(name = "qwen")
	public ChatModel chatModelQwen()
	{
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("qwen/qwen3-coder:free")
//				.modelName("qwen/qwen-plus")
				.baseUrl("https://openrouter.ai/api/v1").build();
	}

	/**
	 * 
	 * @Description: 知識出處，https://api-docs.deepseek.com/zh-cn/
	 * 
	 * 
	 */

	@Bean(name = "deepseek")
	public ChatModel chatModelDeepSeek() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("deepseek"))
				.modelName("deepseek-chat")
				//.modelName("deepseek-reasoner")
				.baseUrl("https://api.deepseek.com/v1").build();
	}
}
