package com.frank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frank.service.ChatCustomModel;

@RestController
public class DeclarativeAIServiceController {

	@Autowired
	private ChatCustomModel chatCustomModel;
	
	
    // http://localhost:9003/lc4j/boot/declarative
    @GetMapping(value = "/lc4j/boot/declarative")
    public String declarative(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        return chatCustomModel.chat(prompt);
    }
}
