package com.example.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度热搜抓取工具
 * 抓取百度热搜榜数据，供 AI 分析使用
 */
@Component
public class BaiduHotSearchTools {

    private static final Logger log = LoggerFactory.getLogger(BaiduHotSearchTools.class);

    /**
     * 抓取百度热搜榜单
     *
     * @return 热搜榜单数据，包含排名、标题、热度等
     */
    @Tool("抓取百度热搜榜单，返回热搜排行榜的完整数据，包括排名、话题标题、热度指数等")
    public String fetchBaiduHotSearch() {
        log.info("开始抓取百度热搜...");
        try {
            List<Map<String, Object>> hotSearchList = new ArrayList<>();

            // 方法1: 抓取百度热搜榜页面
            String url = "https://top.baidu.com/board?tab=realtime";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(15000)
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Referer", "https://www.baidu.com/")
                    .get();

            // 解析热搜条目 - 百度热搜页面结构
            Elements hotItems = doc.select(".container-left .item");

            if (hotItems.isEmpty()) {
                // 备用选择器
                hotItems = doc.select(".c-single-text-ellipsis");
            }
            if (hotItems.isEmpty()) {
                hotItems = doc.select("div[data-idx]");
            }

            int rank = 1;
            for (Element item : hotItems) {
                if (rank > 20) break; // 只取前20条

                try {
                    String title = "";
                    String heat = "";
                    String link = "";

                    // 尝试多种选择器获取标题
                    Element titleEl = item.selectFirst(".c-single-text-ellipsis, .topic-title, h2, .title, a");
                    if (titleEl != null) {
                        title = titleEl.text().trim();
                    }

                    // 获取热度
                    Element heatEl = item.selectFirst(".hot-index, .heat, .score, span");
                    if (heatEl != null) {
                        heat = heatEl.text().trim();
                    }

                    // 获取链接
                    Element linkEl = item.selectFirst("a");
                    if (linkEl != null) {
                        String href = linkEl.attr("href");
                        if (href != null && !href.isEmpty()) {
                            link = href.startsWith("http") ? href : "https://top.baidu.com" + href;
                        }
                    }

                    if (!title.isEmpty()) {
                        Map<String, Object> hotItem = new HashMap<>();
                        hotItem.put("rank", rank);
                        hotItem.put("title", title);
                        hotItem.put("heat", heat);
                        hotItem.put("link", link);
                        hotSearchList.add(hotItem);
                        rank++;
                    }
                } catch (Exception e) {
                    log.debug("解析热搜条目失败: {}", e.getMessage());
                }
            }

            // 如果 JSoup 方法失败，使用备用 API
            if (hotSearchList.isEmpty()) {
                return fetchBaiduHotSearchBackup();
            }

            return formatHotSearchResult(hotSearchList);

        } catch (IOException e) {
            log.error("抓取百度热搜失败: {}", e.getMessage());
            return "抓取失败，请稍后重试。错误信息: " + e.getMessage();
        }
    }

    /**
     * 备用方案：使用百度热搜 API
     */
    private String fetchBaiduHotSearchBackup() {
        try {
            // 尝试百度热搜 API
            String apiUrl = "https://top.baidu.com/api.php?query=百度热搜&format=json&tn=group&ct=1&pcz=1&devi=1&fname=realtime&spm=acf.co2.1.0.0.abc123";

            String jsonResponse = Jsoup.connect(apiUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .ignoreContentType(true)
                    .get()
                    .text();

            // 解析 JSON 并格式化输出
            return parseBaiduApiResponse(jsonResponse);

        } catch (Exception e) {
            log.error("备用 API 抓取失败: {}", e.getMessage());
            return "获取热搜失败: " + e.getMessage();
        }
    }

    /**
     * 解析百度 API 返回的 JSON 数据
     */
    private String parseBaiduApiResponse(String json) {
        // 简单的文本解析（避免引入 JSON 解析库）
        StringBuilder result = new StringBuilder();
        result.append("📊 百度热搜榜\n\n");

        // 提取关键词
        String[] lines = json.split("\"");
        int count = 0;
        for (int i = 0; i < lines.length && count < 20; i++) {
            String line = lines[i].trim();
            if (line.length() > 3 && line.length() < 100 && !line.contains("http") && !line.contains(":")) {
                // 过滤有效标题
                if (isValidTitle(line)) {
                    count++;
                    result.append(String.format("%d. %s\n", count, line));
                }
            }
        }

        if (count == 0) {
            return "暂无法获取热搜数据，请稍后重试。";
        }

        return result.toString();
    }

    /**
     * 判断是否为有效的热搜标题
     */
    private boolean isValidTitle(String text) {
        // 过滤掉无意义的字符串
        if (text.length() < 4 || text.length() > 50) return false;
        if (text.contains("{") || text.contains("}") || text.contains("[")) return false;
        if (text.matches(".*\\d{5,}.*")) return false; // 过滤纯数字
        return true;
    }

    /**
     * 格式化热搜结果
     */
    private String formatHotSearchResult(List<Map<String, Object>> hotSearchList) {
        StringBuilder result = new StringBuilder();
        result.append("📊 百度热搜榜\n\n");

        for (Map<String, Object> item : hotSearchList) {
            int rank = (int) item.get("rank");
            String title = (String) item.get("title");
            String heat = (String) item.get("heat");
            String link = (String) item.get("link");

            result.append(String.format("%d. %s", rank, title));
            if (heat != null && !heat.isEmpty()) {
                result.append(String.format(" (热度: %s)", heat));
            }
            result.append("\n");
        }

        result.append(String.format("\n共 %d 条热搜数据", hotSearchList.size()));
        return result.toString();
    }
}
