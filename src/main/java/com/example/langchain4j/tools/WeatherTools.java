package com.example.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 天气查询工具
 * 使用 @Tool 注解标记方法，LangChain4j 会自动将其注册为 AI 可调用的工具
 */
@Component
public class WeatherTools {

    private static final Map<String, String> WEATHER_DATA = new HashMap<>();

    static {
        WEATHER_DATA.put("北京", "晴天，温度 25°C，北风 3 级");
        WEATHER_DATA.put("上海", "多云，温度 28°C，东南风 2 级");
        WEATHER_DATA.put("广州", "雷阵雨，温度 32°C，南风 4 级");
        WEATHER_DATA.put("深圳", "阴天，温度 30°C，东风 2 级");
        WEATHER_DATA.put("杭州", "小雨，温度 22°C，东北风 3 级");
        WEATHER_DATA.put("成都", "阴天，温度 20°C，微风");
        WEATHER_DATA.put("武汉", "晴天，温度 27°C，西南风 2 级");
        WEATHER_DATA.put("南京", "多云，温度 26°C，东风 3 级");
    }

    /**
     * 查询指定城市的天气信息
     *
     * @param cityName 城市名称
     * @return 天气描述信息
     */
    @Tool("查询指定城市的当前天气信息，包括温度、天气状况和风力")
    public String getWeather(String cityName) {
        String weather = WEATHER_DATA.get(cityName);
        if (weather != null) {
            return String.format("%s今日天气：%s（数据日期：%s）",
                    cityName, weather,
                    LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        return String.format("抱歉，暂未收录 %s 的天气数据。目前支持查询的城市有：北京、上海、广州、深圳、杭州、成都、武汉、南京。", cityName);
    }
}
