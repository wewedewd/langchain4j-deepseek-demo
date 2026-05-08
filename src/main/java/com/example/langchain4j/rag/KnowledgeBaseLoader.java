package com.example.langchain4j.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * RAG 知识库加载器
 * 应用启动时自动加载示例文档到向量存储中
 */
@Component
public class KnowledgeBaseLoader {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeBaseLoader.class);

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public KnowledgeBaseLoader(EmbeddingModel embeddingModel,
                               EmbeddingStore<TextSegment> embeddingStore) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    @PostConstruct
    public void loadKnowledgeBase() {
        log.info("========== 开始加载 RAG 知识库 ==========");

        try {
            // 创建文档分割器：每个分块最大 500 字符，重叠 100 字符
            DocumentSplitter splitter = DocumentSplitters.recursive(500, 100);

            // 创建 Embedding 存储器
            EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                    .documentSplitter(splitter)
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();

            // 加载示例知识文档（纯文本）
            Document doc1 = Document.from(
                    "LangChain4j 是一个 Java 框架，用于将大语言模型（LLM）集成到 Java 应用中。" +
                    "它提供了声明式 AI Services、RAG 检索增强生成、工具调用、对话记忆等核心功能。" +
                    "LangChain4j 支持多种 LLM 提供商，包括 OpenAI、Azure OpenAI、Ollama、Hugging Face 等。" +
                    "通过 Spring Boot Starter，可以轻松地将 LangChain4j 集成到 Spring Boot 项目中。"
            );

            Document doc2 = Document.from(
                    "DeepSeek 是一家中国的人工智能公司，专注于大语言模型的研发。" +
                    "DeepSeek V4 是其最新发布的模型系列，包括 deepseek-v4-pro 和 deepseek-v4-flash。" +
                    "DeepSeek API 兼容 OpenAI 接口协议，因此可以使用 OpenAI SDK 或兼容的客户端直接调用。" +
                    "DeepSeek 的 API 基础地址为 https://api.deepseek.com，支持聊天补全、函数调用等功能。"
            );

            Document doc3 = Document.from(
                    "RAG（Retrieval-Augmented Generation，检索增强生成）是一种结合了信息检索和文本生成的技术。" +
                    "它的工作原理是：首先从知识库中检索与用户问题相关的文档片段，然后将这些片段作为上下文提供给 LLM，" +
                    "让 LLM 基于检索到的信息来生成回答。这样可以有效减少 LLM 的幻觉问题，提高回答的准确性。" +
                    "LangChain4j 提供了完整的 RAG 支持，包括文档加载、文档分割、向量化存储和语义检索。"
            );

            Document doc4 = Document.from(
                    "Spring Boot 是一个基于 Spring 框架的快速开发框架，它简化了 Spring 应用的配置和部署。" +
                    "Spring Boot 3.x 要求 Java 17 及以上版本，支持 GraalVM 原生镜像编译。" +
                    "通过 starter 依赖，可以快速集成各种功能模块。" +
                    "LangChain4j 提供了 langchain4j-open-ai-spring-boot-starter 等 starter，" +
                    "可以通过 application.yml 配置文件来配置模型参数，无需编写大量代码。"
            );

            // 将文档导入向量存储
            ingestor.ingest(doc1, doc2, doc3, doc4);

            log.info("========== RAG 知识库加载完成，共加载 4 篇文档 ==========");

        } catch (Exception e) {
            log.error("RAG 知识库加载失败", e);
        }
    }
}
