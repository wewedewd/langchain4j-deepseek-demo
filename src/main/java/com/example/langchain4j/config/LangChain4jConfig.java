package com.example.langchain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4j 配置类
 * 配置 Embedding Store、ChatMemory 等核心组件
 */
@Configuration
public class LangChain4jConfig {

    /**
     * 内存向量存储 (用于 RAG 演示)
     * 生产环境可替换为 Redis、PgVector、Milvus 等
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    /**
     * ChatMemory 提供器 (用于 MemoryAssistant 的对话记忆)
     * 每个用户 (memoryId) 独立维护一个窗口大小为 10 的对话记忆
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.withMaxMessages(10);
    }

    /**
     * 本地 Embedding 模型 (用于 RAG 文档向量化)
     * 使用 all-MiniLM-L6-v2-q 量化模型，无需联网
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        return new dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel();
    }
}
