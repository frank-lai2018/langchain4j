package com.frank.service;


/**
 * @Description: 我們知道，依照Java開發一般習慣，有介面就要有實作類
 * 例如介面ChatAssistant，就會有實作類別ChatAssistantImpl
 * 現在用高階api-AIServics不用你自己寫impl實作類，交給langchain4j給你搞定
 *
 * 本次配置用的是langchain4j原生整合，沒有引入sprinboot，不需要介面頭上配置@AiService註解標籤
 */
public interface MyAssistant {
	 String chat(String userMessage);
}
