package com.frank.controller;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frank.service.ChatAssistant;

import reactor.core.publisher.Flux;

/**
 * @Description: 知識出處，https://docs.langchain4j.dev/tutorials/response-streaming
 */
@RestController
@Slf4j
public class StreamingChatModelController
{
	 @Resource //直接使用 low-level LLM API
	 private StreamingChatModel streamingChatLanguageModel;
	 @Resource //自己封裝介面使用 high-level LLM API
	 private ChatAssistant chatAssistant;


    // http://localhost:9007/chatstream/chat?prompt=台北有什麼好吃的
	 //使用低階API來調用串流對話模型
    @GetMapping(value = "/chatstream/chat")
    public Flux<String> chat(@RequestParam("prompt") String prompt)
    {
        System.out.println("---come in chat");

        return Flux.create(emitter -> {
            streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler()
            {
                @Override
                public void onPartialResponse(String partialResponse)
                {
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse)
                {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable throwable)
                {
                    emitter.error(throwable);
                }
            });
        });
    }

    @GetMapping(value = "/chatstream/chat2")
    public void chat2(@RequestParam(value = "prompt", defaultValue = "台北有什麼好吃的") String prompt)
    {
        System.out.println("---come in chat2");
        streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler()
        {
            @Override
            public void onPartialResponse(String partialResponse)
            {
                System.out.println(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse)
            {
                System.out.println("---response over: "+completeResponse);
            }

            @Override
            public void onError(Throwable throwable)
            {
                throwable.printStackTrace();
            }
        });
    }


    //使用高階API來調用串流對話模型
    @GetMapping(value = "/chatstream/chat3")
    public Flux<String> chat3(@RequestParam(value = "prompt", defaultValue = "台北有什麼好吃的") String prompt)
    {
        System.out.println("---come in chat3");

        return chatAssistant.chatFlux(prompt);
    }
}
