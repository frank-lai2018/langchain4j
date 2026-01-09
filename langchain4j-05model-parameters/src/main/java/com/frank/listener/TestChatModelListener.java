package com.frank.listener;

import cn.hutool.core.util.IdUtil;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 知識出處，https://docs.langchain4j.dev/tutorials/spring-boot-integration#observability
 */
@Slf4j
public class TestChatModelListener implements ChatModelListener {
	@Override
	public void onRequest(ChatModelRequestContext requestContext) {

		// onRequest配置的k:v鍵值對，在onResponse階段可以取得，上下文傳遞參數好用
		String uuidValue = IdUtil.simpleUUID();
		requestContext.attributes().put("TraceID", uuidValue);
		log.info("請求參數requestContext:{}", requestContext + "\t" + uuidValue);
	}

	@Override
	public void onResponse(ChatModelResponseContext responseContext) {
		Object object = responseContext.attributes().get("TraceID");

		log.info("返回結果responseContext:{}", object);
	}

	@Override
	public void onError(ChatModelErrorContext errorContext) {
		log.error("請求異常ChatModelErrorContext:{}", errorContext);
	}
}
