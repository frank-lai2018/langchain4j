package com.frank.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatCustomModel {

	String chat(String prompt);
}
