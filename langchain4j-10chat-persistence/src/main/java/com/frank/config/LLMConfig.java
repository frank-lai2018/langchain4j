package com.frank.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.ChatPersistenceAssistant;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;

@Configuration
public class LLMConfig {
	
    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

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
    public ChatPersistenceAssistant chatMemoryAssistant(ChatModel chatModel)
    {

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(1000)
                .chatMemoryStore(redisChatMemoryStore)
                .build();

        return AiServices.builder(ChatPersistenceAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }

}
