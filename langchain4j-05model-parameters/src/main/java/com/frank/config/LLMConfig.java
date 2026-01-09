package com.frank.config;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.listener.TestChatModelListener;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class LLMConfig {

	/**
	 * 使用的https://openrouter.ai/qwen/qwen3-coder:free/api
	 * 	 * */
	@Bean
	public ChatModel chatModelQwen() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("qwen/qwen3-coder:free")
				.logRequests(true) // 日誌等級設定為debug才有效
				.logResponses(true)// 日誌等級設定為debug才有效
				.listeners(Arrays.asList(new TestChatModelListener()))
				.maxRetries(1)
				.timeout(Duration.ofSeconds(2))//向大模型發送請求時，如在指定時間內沒有收到回應，該請求將被中斷並報request timed out
//				.modelName("qwen/qwen-plus")
				.baseUrl("https://openrouter.ai/api/v1").build();
	}
}
