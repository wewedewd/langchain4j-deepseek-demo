package com.example.langchain4j.controller;

import com.example.langchain4j.service.HotSearchAgent;
import com.example.langchain4j.tools.BaiduHotSearchTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 百度热搜控制器
 * 提供热搜抓取和 AI 分析功能
 */
@RestController
@RequestMapping("/api/hotsearch")
public class HotSearchController {

    private final HotSearchAgent hotSearchAgent;
    private final BaiduHotSearchTools baiduHotSearchTools;

    public HotSearchController(HotSearchAgent hotSearchAgent,
                              BaiduHotSearchTools baiduHotSearchTools) {
        this.hotSearchAgent = hotSearchAgent;
        this.baiduHotSearchTools = baiduHotSearchTools;
    }

    /**
     * AI 分析热搜
     * GET /api/hotsearch/analyze?question=分析今天的热搜趋势
     *
     * AI 会自动调用热搜抓取工具并进行分析
     */
    @GetMapping("/analyze")
    public String analyzeHotSearch(@RequestParam(defaultValue = "抓取百度热搜并分析今日热点话题") String question) {
        return hotSearchAgent.analyzeHotSearch(question);
    }

    /**
     * 直接抓取热搜榜单（不经过 AI）
     * GET /api/hotsearch/fetch
     */
    @GetMapping("/fetch")
    public String fetchHotSearch() {
        return baiduHotSearchTools.fetchBaiduHotSearch();
    }
}
