package com.example.langchain4j.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 带 Tool 工具调用的 AI Service
 * LangChain4j 会自动扫描 @Component 类中标注了 @Tool 的方法
 * 并将其注册为可用工具，AI 可以根据用户意图自动调用
 */
@AiService
public interface ToolAssistant {

    @SystemMessage("你是一个智能助手，可以查询天气和进行数学计算。" +
            "当用户询问天气时，使用天气查询工具；" +
            "当用户需要进行数学计算时，使用计算器工具。" +
            "请用中文回答。")
    String chat(@UserMessage String userMessage);
}
