package com.frank.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;


public interface ChatPersistenceAssistant
{
	/**
	 * 聊天
	 *
	 * @param userId 使用者 ID
	 * @param message 訊息
	 * @return {@link String }
	 */
    String chat(@MemoryId Long userId, @UserMessage String message);
}

