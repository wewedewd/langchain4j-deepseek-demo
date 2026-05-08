package com.example.langchain4j.controller;

import com.example.langchain4j.service.ToolAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工具调用控制器
 * 演示 LangChain4j 的 Tool Calling 能力
 * AI 可以自动调用天气查询和计算器工具
 */
@RestController
@RequestMapping("/api/tool")
public class ToolController {

    private final ToolAssistant toolAssistant;

    public ToolController(ToolAssistant toolAssistant) {
        this.toolAssistant = toolAssistant;
    }

    /**
     * 工具调用对话接口
     * 示例请求：
     * - /api/tool?message=北京今天天气怎么样
     * - /api/tool?message=帮我算一下 (123 + 456) * 2
     * - /api/tool?message=上海天气如何？顺便算一下 100 / 3
     */
    @GetMapping
    public String chat(@RequestParam String message) {
        return toolAssistant.chat(message);
    }
}
