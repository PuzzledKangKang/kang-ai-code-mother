package net.pkk.kangaicodemother.config;

import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 持久化对话记忆
 *
 * @author 林子康
 * @version 1.0
 * @since 2025/8/15 10:28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisChatMemoryStoreConfig {

    /**
     * Redis 连接地址
     */
    private String host;

    /**
     * Redis 连接端口 default 6379
     */
    private int port;

    /**
     * Redis 连接密码
     */
    private String password;

    /**
     * 超时时间
     */
    private long ttl;

    @Bean
    public RedisChatMemoryStore redisChatMemoryStore() {
        return RedisChatMemoryStore.builder()
                .host(host)
                .port(port)
                .ttl(ttl)
                .password(password)
                .build();
    }
}
