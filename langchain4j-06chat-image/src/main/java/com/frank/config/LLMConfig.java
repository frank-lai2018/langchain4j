package com.frank.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 知識出處
 * https://docs.langchain4j.dev/tutorials/chat-and-language-models/#image-content
 */
@Configuration
public class LLMConfig
{
	@Bean(name = "chatModel")
	public ChatModel chatModel() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("google/gemini-2.0-flash-exp:free")
				.baseUrl("https://openrouter.ai/api/v1").build();
	}

/*
	@Bean(name = "deepseek")
	public ChatModel chatModelDeepSeek() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("deepseek"))
				.modelName("deepseek-chat")
				//.modelName("deepseek-reasoner")
				.baseUrl("https://api.deepseek.com/v1").build();
	}
*/

}