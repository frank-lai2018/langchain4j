package com.frank.service;

import com.frank.entity.LawPrompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface LawAssistant
{
    //案例1 @SystemMessage+@UserMessage+@V
    @SystemMessage("你是一個專業的中華民國法律顧問，只回答中華民國法律相關問題。" +
            "輸出限制：對於其他領域的問題拒絕回答，直接返回'Sorry，我是專業的法路顧問，所以我只能回答法律問題。'")

    @UserMessage("請回答以下法律問題：{{question}},字數控制在{{length}}以內")

    String chat(@V("question") String question, @V("length") int length);


//    //案例2 新建帶著@StructuredPrompt的業務實體類，例如LawPrompt
    @SystemMessage("你是一個專業的中華民國法律顧問，只回答中華民國法律相關問題。" +
            "輸出限制：對於其他領域的問題拒絕回答，直接返回'Sorry，我是專業的法路顧問，所以我只能回答法律問題。'")
    String chat(LawPrompt lawPrompt);
}