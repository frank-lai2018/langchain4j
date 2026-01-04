package com.frank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ModelParameterController {
	
	@Resource
	private ChatModel deepseekMode;

	@GetMapping("/dis")
	public String chat(@RequestParam(value = "prompt", defaultValue = "你是誰?") String prompt) {
		String result = deepseekMode.chat(prompt);
		
		return result;
	}
}
