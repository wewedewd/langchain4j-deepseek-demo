package com.example.langchain4j.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 基础对话 AI Service
 * 使用 @AiService 注解声明式定义 AI 服务
 * LangChain4j 会自动扫描并生成实现类
 */
@AiService
public interface ChatAssistant {

    @SystemMessage("你是一个友好、专业的 AI 助手。请用中文回答用户的问题。")
    String chat(@UserMessage String userMessage);
}
