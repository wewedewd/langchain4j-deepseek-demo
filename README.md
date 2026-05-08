# LangChain4j + Spring Boot + DeepSeek 示例项目

基于 LangChain4j 框架，通过腾讯云 TokenHub 接入 DeepSeek V4 大模型的完整示例项目。

## 技术栈

| 组件 | 版本 |
|------|------|
| Java | 17+ |
| Spring Boot | 3.2.5 |
| LangChain4j | 1.0.0-beta1 |
| LLM | DeepSeek V4-Pro (腾讯云 TokenHub) |

## 功能特性

- ✅ **基础对话** - 与 DeepSeek V4 模型进行对话
- ✅ **对话记忆** - 多轮对话，记住上下文
- ✅ **工具调用** - AI 自动调用天气查询、计算器等工具
- ✅ **RAG 检索增强** - 从知识库检索相关内容辅助回答

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.6+

### 2. 配置 API Key

在 `application.yml` 中配置腾讯云 TokenHub API Key：

```yaml
langchain4j:
  open-ai:
    chat-model:
      base-url: https://tokenhub.tencentmaas.com/v1
      api-key: ${TENCENT_LKEAP_API_KEY:your-api-key-here}
      model-name: deepseek-v4-pro
```

或设置环境变量：

```bash
# Windows
set TENCENT_LKEAP_API_KEY=your-api-key

# Linux/Mac
export TENCENT_LKEAP_API_KEY=your-api-key
```

### 3. 运行项目

```bash
cd langchain4j-deepseek-demo
mvn spring-boot:run
```

### 4. 测试接口

| 功能 | 接口 | 示例 |
|------|------|------|
| 基础对话 | `GET /api/chat?message=你好` | [测试](http://localhost:8080/api/chat?message=你好) |
| 对话记忆 | `GET /api/memory?userId=alice&message=我叫张三` | 多轮对话记住用户信息 |
| 工具调用 | `GET /api/tool?message=北京天气` | AI 自动调用天气工具 |
| RAG 检索 | `GET /api/rag?message=LangChain4j是什么` | 从知识库检索回答 |

## 项目结构

```
langchain4j-deepseek-demo/
├── pom.xml
├── src/main/
│   ├── resources/application.yml
│   └── java/com/example/langchain4j/
│       ├── Langchain4jDeepseekApplication.java  # 启动类
│       ├── config/LangChain4jConfig.java        # 配置类
│       ├── service/                             # AI Services
│       │   ├── ChatAssistant.java               # 基础对话
│       │   ├── MemoryAssistant.java             # 带记忆
│       │   ├── ToolAssistant.java               # 工具调用
│       │   └── RagAssistant.java                # RAG
│       ├── tools/                               # 工具类
│       │   ├── WeatherTools.java
│       │   └── CalculatorTools.java
│       ├── rag/KnowledgeBaseLoader.java         # 知识库加载
│       └── controller/                          # REST 控制器
```

## 可用模型

腾讯云 TokenHub 支持的 DeepSeek 模型：

| 模型 | model-name | 特点 |
|------|------------|------|
| DeepSeek V4-Pro | `deepseek-v4-pro` | 最强性能，1M 上下文 |
| DeepSeek V4-Flash | `deepseek-v4-flash` | 更快速度 |
| DeepSeek V3.2 | `deepseek-v3.2` | 稳定版本 |
| DeepSeek R1 | `deepseek-r1-0528` | 推理增强 |

## License

MIT
