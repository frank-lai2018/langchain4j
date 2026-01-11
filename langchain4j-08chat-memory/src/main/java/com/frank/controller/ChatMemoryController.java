package com.frank.controller;

import cn.hutool.core.date.DateUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frank.service.MyAssistant;
import com.frank.service.MyMemoryAssistant;

import java.util.Arrays;

@RestController
@Slf4j
public class ChatMemoryController {
	@Resource(name = "chat")
	private MyAssistant chatAssistant;

	@Resource(name = "chatMessageWindowChatMemory")
	private MyMemoryAssistant chatMessageWindowChatMemory;

	@Resource(name = "chatTokenWindowChatMemory")
	private MyMemoryAssistant chatTokenWindowChatMemory;

	/**
	 * @Description: v1,沒有記憶快取功能 http://localhost:9008/chatmemory/test1
	 */
	@GetMapping(value = "/chatmemory/test1")
	public String chat() {

		String answer01 = chatAssistant.chat("你好，我的名字叫張三");
		System.out.println("answer01回傳結果：" + answer01);

		String answer02 = chatAssistant.chat("我的名字是什麼");
		System.out.println("answer02回傳結果：" + answer02);

		return "success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01 + "<br> \n\n answer02: " + answer02;
	}

	/**
	 * @Description: MessageWindowChatMemory實作聊天功能
	 */
	@GetMapping(value = "/chatmemory/test2")
	public String chatMessageWindowChatMemory() {
		chatMessageWindowChatMemory.chatWithChatMemory(1L, "你好！我的名字是Java.");
		String answer01 = chatMessageWindowChatMemory.chatWithChatMemory(1L, "我的名字是什麼");
		System.out.println("answer01回傳結果：" + answer01);

		chatMessageWindowChatMemory.chatWithChatMemory(3L, "你好！我的名字是C++");
		String answer02 = chatMessageWindowChatMemory.chatWithChatMemory(3L, "我的名字是什麼");
		System.out.println("answer02回傳結果：" + answer02);

		return "chatMessageWindowChatMemory success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01
				+ "<br> \n\n answer02: " + answer02;

	}

	/**
	 * @Description: TokenWindowChatMemory實作聊天功能
	 */
	@GetMapping(value = "/chatmemory/test3")
	public String chatTokenWindowChatMemory() {
		chatTokenWindowChatMemory.chatWithChatMemory(1L, "你好！我的名字是mysql");
		String answer01 = chatTokenWindowChatMemory.chatWithChatMemory(1L, "我的名字是什麼");
		System.out.println("answer01回傳結果：" + answer01);

		chatTokenWindowChatMemory.chatWithChatMemory(3L, "你好！我的名字是oracle");
		String answer02 = chatTokenWindowChatMemory.chatWithChatMemory(3L, "我的名字是什麼");
		System.out.println("answer02回傳結果：" + answer02);

		return "chatTokenWindowChatMemory success : " + DateUtil.now() + "<br> \n\n answer01: " + answer01
				+ "<br> \n\n answer02: " + answer02;
	}
}
