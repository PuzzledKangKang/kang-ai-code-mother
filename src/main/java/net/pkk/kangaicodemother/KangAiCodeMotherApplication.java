package net.pkk.kangaicodemother;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("net.pkk.kangaicodemother.mapper")
public class KangAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangAiCodeMotherApplication.class, args);
    }

}
