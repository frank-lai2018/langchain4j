package com.frank.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frank.entity.LawPrompt;
import com.frank.service.LawAssistant;

import cn.hutool.core.date.DateUtil;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ChatPromptController {

	@Resource
	private LawAssistant lawAssistant;
	
    @Resource
    private ChatModel chatModel;

	// http://localhost:9009/chatprompt/test1
	@GetMapping(value = "/chatprompt/test1")
	public String test1() {
		String chat = lawAssistant.chat("什麼是智慧財產？", 2000);
		System.out.println(chat);

		String chat2 = lawAssistant.chat("什麼事java？", 2000);
		System.out.println(chat2);

		String chat3 = lawAssistant.chat("介紹一下台北小吃", 2000);
		System.out.println(chat3);

		return "success : " + DateUtil.now() + "<br> \n\n chat: " + chat + "<br> \n\n chat2: " + chat2;
	}

	/**
	 * TRIPS協定（與貿易有關的智慧財產權協定）： 這是世界貿易組織（WTO）成員間的一個重要協議，
	 * 它規定了最低標準的智慧財產權保護要求，並適用於所有WTO成員。
	 * 
	 * @return
	 */
	@GetMapping(value = "/chatprompt/test2")
	public String test2() {
		LawPrompt prompt = new LawPrompt();

		prompt.setLegal("知識產權");
		prompt.setQuestion("TRIPS協議?");

		prompt.setFormat("html");
		String chat = lawAssistant.chat(prompt);

		System.out.println(chat);

		return "success : " + DateUtil.now() + "<br> \n\n chat: " + chat;
	}
	
	
	/**
	 * @Description: 單一參數可以使用{{it}》”佔位符或”{{參數名}”，如果為其他字符，系統不能自動辨識會報錯。
	 *  http://localhost:9009/chatprompt/test3
	 */
	@GetMapping(value = "/chatprompt/test3")
	public String test3() {
		// 看看原始碼，預設 PromptTemplate 建構使用 it 屬性作為預設佔位符

		/*
		 * String role = "外科醫生"; String question = "牙痛";
		 */
//		String role = "外科醫生"; 
//		String question = "牙痛";
//		String role = "財務會計";
//		String question = "新台幣大寫";
		
		String role = "法律顧問"; 
		String question = "智慧財產權包括哪些範疇？";
		
		// 1. 建立 SystemMessage
		SystemMessage systemMessage = SystemMessage
				.from("你是一個專業的中華民國法律顧問，只回答中華民國法律相關問題。" + "輸出限制：對於其他領域的問題拒絕回答，直接返回'Sorry，我是專業的法路顧問，所以我只能回答法律問題。'");

		// 1 建構PromptTemplate模板
		PromptTemplate template = PromptTemplate.from("你是一個{{it}}助手,{{question}}怎麼辦，請以{{format}}格式回答我。}");
		// 2 由PromptTemplate產生Prompt
		Prompt prompt = template.apply(Map.of("it", role, "question", question,"format","html"));
		// 3 Prompt提示詞變成UserMessage
		UserMessage userMessage = prompt.toUserMessage();
		// 4 呼叫大模型
		ChatResponse chatResponse = chatModel.chat(systemMessage,userMessage);

		// 4.1 後台列印
		System.out.println(chatResponse.aiMessage().text());
		// 4.2 前台返回
		return "success : " + DateUtil.now() + "<br> \n\n chat: " + chatResponse.aiMessage().text();
	}
}
