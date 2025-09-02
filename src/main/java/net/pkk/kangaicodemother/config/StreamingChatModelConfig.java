package net.pkk.kangaicodemother.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 流式对话模型配置
 * @author 林子康
 * @version 1.0
 * @since 2025/9/2 9:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "langchain4j.open-ai.streaming-chat-model")
public class StreamingChatModelConfig {

    /**
     * 调用大模型的地址
     */
    private String baseUrl;

    /**
     * 调用大模型的专属密钥
     */
    private String apiKey;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 请求的最大令牌数
     */
    private Integer maxTokens;

    /**
     *
     */
    private Double temperature;

    /**
     * 请求日志
     */
    private boolean logRequests;

    /**
     * 响应日志
     */
    private boolean logResponses;

    @Bean
    @Scope("prototype")
    public StreamingChatModel streamingChatModelPrototype() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .maxTokens(maxTokens)
                .temperature(temperature)
                .logRequests(logRequests)
                .logResponses(logResponses)
                .build();
    }
}
