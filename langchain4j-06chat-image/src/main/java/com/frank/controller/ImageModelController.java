package com.frank.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

/**
 * @Description: https://docs.langchain4j.dev/tutorials/chat-and-language-models/#multimodality
 */
@RestController
@Slf4j
public class ImageModelController {
	@Autowired
	private ChatModel chatModel;

	@Value("classpath:static/1795.jpg")
	private Resource resource;// import org.springframework.core.io.Resource;

	/**
	 * @Description: 透過Base64編碼將圖片轉換為字串
	 *               結合ImageContent和TextContent形成UserMessage一起傳送到模型進行處理。
	 *
	 *          測試位址：http://localhost:9006/image/call
	 */
	@GetMapping(value = "/image/call")
	public String readImageContent() throws IOException {
		String result = null;

		System.out.println(resource);
		// 第一步，圖片轉碼：透過Base64編碼將圖片轉換為字串
		byte[] byteArray = resource.getContentAsByteArray();
		String base64Data = Base64.getEncoder().encodeToString(byteArray);

		// 第二步，提示詞指定：結合ImageContent和TextContent一起傳送到模型處理。
		UserMessage userMessage = UserMessage.from(TextContent.from("請分析圖片上的股價趨勢"),
				ImageContent.from(base64Data, "image/jpg"));
		// 第三步，API呼叫：使用OpenAiChatModel來建構請求，並透過chat()方法呼叫模型。
		// 請求內容包含文字提示和圖片，模型會根據輸入回傳分析結果。
		ChatResponse chatResponse = chatModel.chat(userMessage);

		// 第四步，解析與輸出：從ChatResponse取得AI大模型的回复，列印出處理後的結果。
		result = chatResponse.aiMessage().text();

		// 後台列印
		System.out.println(result);

		// 回前台
		return result;
	}
}
