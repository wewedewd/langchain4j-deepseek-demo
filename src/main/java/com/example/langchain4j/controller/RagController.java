package com.example.langchain4j.controller;

import com.example.langchain4j.service.RagAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RAG 检索增强生成控制器
 * 演示 LangChain4j 的 RAG 能力
 * AI 会从知识库中检索相关内容来辅助回答
 */
@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagAssistant ragAssistant;

    public RagController(RagAssistant ragAssistant) {
        this.ragAssistant = ragAssistant;
    }

    /**
     * RAG 问答接口
     * 示例请求：
     * - /api/rag?message=LangChain4j是什么？
     * - /api/rag?message=DeepSeek V4支持哪些模型？
     * - /api/rag?message=RAG的工作原理是什么？
     * - /api/rag?message=Spring Boot 3需要什么Java版本？
     */
    @GetMapping
    public String chat(@RequestParam String message) {
        return ragAssistant.chat(message);
    }
}
