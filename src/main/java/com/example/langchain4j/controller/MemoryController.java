package com.example.langchain4j.controller;

import com.example.langchain4j.service.MemoryAssistant;
import org.springframework.web.bind.annotation.*;

/**
 * 带记忆功能的对话控制器
 * 演示 LangChain4j 的 ChatMemory 对话记忆能力
 * 通过 memoryId 区分不同用户的对话上下文
 */
@RestController
@RequestMapping("/api/memory")
public class MemoryController {

    private final MemoryAssistant memoryAssistant;

    public MemoryController(MemoryAssistant memoryAssistant) {
        this.memoryAssistant = memoryAssistant;
    }

    /**
     * 带记忆的对话接口
     * GET /api/memory?userId=alice&message=我叫张三
     * GET /api/memory?userId=alice&message=我叫什么名字？
     *
     * @param userId  用户标识（用于隔离不同用户的记忆）
     * @param message 用户消息
     * @return AI 回复
     */
    @GetMapping
    public String chat(@RequestParam String userId,
                       @RequestParam String message) {
        return memoryAssistant.chat(userId, message);
    }
}
