package com.example.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * 计算器工具
 * 演示 LangChain4j 的 Tool 调用能力
 */
@Component
public class CalculatorTools {

    /**
     * 执行简单的数学计算
     *
     * @param expression 数学表达式，例如 "2 + 3 * 4"
     * @return 计算结果
     */
    @Tool("执行简单的数学计算，支持加减乘除运算。例如：2 + 3, 10 * 5, 100 / 4")
    public String calculate(String expression) {
        try {
            // 简单安全的表达式计算（仅支持基本四则运算）
            if (!expression.matches("[\\d+\\-*/().\\s]+")) {
                return "错误：表达式包含不支持的字符，仅支持数字和 + - * / ( ) 运算符";
            }

            // 使用 ScriptEngine 安全评估
            javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager()
                    .getEngineByName("js");
            if (engine != null) {
                Object result = engine.eval(expression);
                return String.format("%s = %s", expression, result);
            }
            return "错误：计算引擎初始化失败";
        } catch (Exception e) {
            return String.format("计算错误：%s，请检查表达式格式是否正确", e.getMessage());
        }
    }
}
