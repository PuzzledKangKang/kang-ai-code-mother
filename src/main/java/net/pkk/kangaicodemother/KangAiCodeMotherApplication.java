package net.pkk.kangaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.pkk.kangaicodemother.mapper")
public class KangAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangAiCodeMotherApplication.class, args);
    }

}
