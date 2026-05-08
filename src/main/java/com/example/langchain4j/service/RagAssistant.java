package com.example.langchain4j.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * RAG (检索增强生成) AI Service
 * 自动关联 ContentRetriever，从知识库中检索相关内容辅助回答
 */
@AiService
public interface RagAssistant {

    @SystemMessage("你是一个知识库问答助手。请根据提供的参考信息来回答用户问题。" +
            "如果参考信息中没有相关内容，请如实告知用户。" +
            "请用中文回答。")
    String chat(@UserMessage String userMessage);
}
