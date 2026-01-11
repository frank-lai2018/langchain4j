package com.frank.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.MyAssistant;
import com.frank.service.MyMemoryAssistant;

@Configuration
public class LLMConfig {
	// 調用openrouter的Qwen模型
	@Bean(name = "qwen")
	public ChatModel chatModelQwen() {
		return OpenAiChatModel.builder().apiKey(System.getenv("openrouter")).modelName("qwen/qwen3-coder:free")
//				.modelName("qwen/qwen-plus")
				.baseUrl("https://openrouter.ai/api/v1").build();
	}

	@Bean(name = "google")
	public ChatModel chatModelGoogle() {
		return OpenAiChatModel.builder().apiKey(System.getenv("openrouter")).modelName("google/gemma-3n-e2b-it:free")
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
		return OpenAiChatModel.builder().apiKey(System.getenv("deepseek")).modelName("deepseek-chat")
				// .modelName("deepseek-reasoner")
				.baseUrl("https://api.deepseek.com/v1").build();
	}
	
    @Bean(name = "chat")
    public MyAssistant chatAssistant(@Qualifier("qwen") ChatModel chatModel)
    {
        return AiServices.create(MyAssistant.class, chatModel);
    }

	/**
	 * @Description: 依照MessageWindowChatMemory,
	 *               知識出處，https://docs.langchain4j.dev/tutorials/chat-memory/#eviction-policy
	 */
	@Bean(name = "chatMessageWindowChatMemory")
	public MyMemoryAssistant chatMessageWindowChatMemory(@Qualifier("google") ChatModel chatModel) {

		return AiServices.builder(MyMemoryAssistant.class).chatModel(chatModel)
				// 依照memoryId對應建立了一個chatMemory
				.chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100)).build();
	}

	/**
	 * @Description: 依照TokenWindowChatMemory,
	 *               知識出處，https://docs.langchain4j.dev/tutorials/chat-memory/#eviction-policy
	 */
	@Bean(name = "chatTokenWindowChatMemory")
	public MyMemoryAssistant chatTokenWindowChatMemory(@Qualifier("google") ChatModel chatModel) {
		// 1 TokenCountEstimator預設的token分詞器，需要結合Tokenizer計算ChatMessage的token數量
		TokenCountEstimator openAiTokenCountEstimator = new OpenAiTokenCountEstimator("gpt-4");

		return AiServices.builder(MyMemoryAssistant.class)
				.chatModel(chatModel)
				.chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(1000, openAiTokenCountEstimator))
				.build();
	}
}
