package com.frank.config;


import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frank.service.FunctionAssistant;
import com.frank.service.InvoiceHandler;

import java.time.Duration;
import java.util.Map;

@Configuration
public class LLMConfig
{
	@Bean
	public ChatModel chatModel() {
		return OpenAiChatModel.builder()
				.apiKey(System.getenv("openrouter"))
				.modelName("qwen/qwen3-coder:free")
				.timeout(Duration.ofSeconds(600))
				.logRequests(true)
				.logResponses(true)
				.baseUrl("https://openrouter.ai/api/v1").build();
	}

    /**
    * @Description: 第一组 Low Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#low-level-tool-api
    */
    @Bean
    public FunctionAssistant functionAssistant(ChatModel chatModel)
    {
        // 工具說明 ToolSpecification
    	ToolSpecification toolSpecification = ToolSpecification.builder()
    			 .name("開立發票助手")
    			 .description("根據使用者提交的開立發票訊息，開立發票")
    			 .parameters(JsonObjectSchema.builder()
	    			 .addStringProperty("companyName", "公司名稱")
	    			 .addStringProperty("dutyNumber", "稅號序列")
	    			 .addStringProperty("amount", "開票金額，保留兩位有效數字")
	    			 .build())
    			 .build();

        // 業務邏輯 ToolExecutor
        ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
            System.out.println("toolExecutionRequest:"+toolExecutionRequest);
            System.out.println(toolExecutionRequest.id());
            System.out.println(toolExecutionRequest.name());
            String arguments1 = toolExecutionRequest.arguments();
            System.out.println("arguments1****》 " + arguments1);
            return "開立發票成功！";
        };

        return AiServices.builder(FunctionAssistant.class)
                .chatModel(chatModel)
                .tools(Map.of(toolSpecification, toolExecutor)) // Tools (Function Calling)
                .build();
    }



    /**
    * @Description: 第二组 High Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#high-level-tool-api
    */
    @Bean
    public FunctionAssistant functionAssistant1(ChatModel chatModel)
    {
        return AiServices.builder(FunctionAssistant.class)
                    .chatModel(chatModel)
                    .tools(new InvoiceHandler())
                .build();
    }
}
