package com.frank.entity;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;

@Data
@StructuredPrompt("根據中華民國{{legal}}法律，解答以下問題：{{question}},並以{{format}}格式進行回答。")
public class LawPrompt {
    private String legal;
    private String question;
    private String format;
}
