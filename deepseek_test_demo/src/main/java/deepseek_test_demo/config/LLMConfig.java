package deepseek_test_demo.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 知識出處 https://docs.langchain4j.dev/get-started
 */
@Configuration
public class LLMConfig
{
//    @Bean(name = "qwen")
//    public ChatModel chatModelQwen()
//    {
//        return OpenAiChatModel.builder()
//                    .apiKey(System.getenv("deepseek-api"))
//                    .modelName("qwen-plus")
//                    .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
//                .build();
//    }

    /**
    * @Description: 知識出處，https://api-docs.deepseek.com/zh-cn/
    */
    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek()
    {
    	System.out.println("66666666666666---"+System.getenv("deepseek-api"));
        return  OpenAiChatModel.builder()
                        .apiKey(System.getenv("deepseek-api"))
                        .modelName("deepseek-chat")
                        //.modelName("deepseek-reasoner")
                        .baseUrl("https://api.deepseek.com/v1")
                .build();
    }
}
 
