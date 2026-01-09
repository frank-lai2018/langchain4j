package com.frank.service;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.springframework.stereotype.Service;

/**
 * 知識出處，https://docs.langchain4j.dev/tutorials/spring-boot-integration/#spring-boot-starter-for-declarative-ai-services
 */
@AiService
public interface ChatAssistant
{
    String chat(String prompt);
}
