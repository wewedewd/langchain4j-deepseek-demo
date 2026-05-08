package com.example.langchain4j.controller;

import com.example.langchain4j.service.ChatAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础对话控制器
 * 演示最简单的 AI 对话功能
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatAssistant chatAssistant;

    public ChatController(ChatAssistant chatAssistant) {
        this.chatAssistant = chatAssistant;
    }

    /**
     * 基础对话接口
     * GET /api/chat?message=你好
     */
    @GetMapping
    public String chat(@RequestParam String message) {
        return chatAssistant.chat(message);
    }
}
