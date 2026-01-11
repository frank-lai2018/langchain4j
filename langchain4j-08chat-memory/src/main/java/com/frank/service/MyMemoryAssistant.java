package com.frank.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface MyMemoryAssistant {
	/**
	 * 聊天帶記憶快取功能
	 *
	 * @param userId 使用者 ID
	 * @param prompt 訊息
	 * @return {@link String }
	 */
	 String chatWithChatMemory(@MemoryId Long userId, @UserMessage String prompt);
}
