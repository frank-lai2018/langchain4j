package com.frank.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.ChatAssistant;

import java.time.Duration;
import java.util.List;

/**
 * @Description: 知識出處，https://docs.langchain4j.dev/tutorials/response-streaming
 */
@Configuration
public class LLMConfig {

	/**
	 * 
	 * @Description: 普通對話介面 ChatModel
	 * 
	 */

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
	 * @Description: 串流對話介面 StreamingChatModel
	 * 
	 */

	@Bean

	public StreamingChatModel streamingChatModel() {

		return OpenAiStreamingChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("qwen/qwen3-coder:free")
//				.modelName("qwen/qwen-plus")
				.baseUrl("https://openrouter.ai/api/v1").build();

	}

	@Bean

	public ChatAssistant chatAssistant(StreamingChatModel streamingChatModel) {

		return AiServices.create(ChatAssistant.class, streamingChatModel);

	}
}
