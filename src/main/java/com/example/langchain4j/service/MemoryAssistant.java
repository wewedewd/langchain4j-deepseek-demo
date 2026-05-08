package com.example.langchain4j.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 带记忆功能的 AI Service
 * 使用 @MemoryId 注解实现多用户独立记忆
 * 每个用户 (memoryId) 维护独立的对话历史
 */
@AiService
public interface MemoryAssistant {

    @SystemMessage("你是一个有记忆能力的 AI 助手。你能记住与用户的对话历史，" +
            "并在后续对话中引用之前的内容。请用中文回答。")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
