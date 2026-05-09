package com.example.langchain4j.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 热搜分析智能体
 * AI 自动调用热搜抓取工具，获取最新热搜并进行深度分析
 */
@AiService
public interface HotSearchAgent {

    @SystemMessage("你是一个专业的热点话题分析师。能够：\n" +
            "1. 主动调用热搜抓取工具获取最新热搜榜单\n" +
            "2. 分析热搜话题背后的原因和趋势\n" +
            "3. 识别热点话题的类别（娱乐、社会、科技、体育等）\n" +
            "4. 总结当日热点，帮助用户快速了解网络热点\n" +
            "请用中文回答，保持简洁有条理。")
    String analyzeHotSearch(@UserMessage String userMessage);
}
