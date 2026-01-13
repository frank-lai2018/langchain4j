package com.frank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 將客戶和大模型的對話問答保存進Redis進行持久化記憶留存
 * 知識出處,https://docs.langchain4j.dev/tutorials/chat-memory#persistence
 */
@SpringBootApplication
public class ChatPersistenceLangChain4JApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ChatPersistenceLangChain4JApp.class,args);
    }
}
