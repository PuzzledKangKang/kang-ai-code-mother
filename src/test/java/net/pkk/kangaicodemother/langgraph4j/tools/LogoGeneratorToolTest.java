package net.pkk.kangaicodemother.langgraph4j.tools;

import jakarta.annotation.Resource;
import net.pkk.kangaicodemother.langgraph4j.model.ImageResource;
import net.pkk.kangaicodemother.langgraph4j.model.enums.ImageCategoryEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: kangkang
 * @Date: 2025/8/29 - 10:14
 * @Description: net.pkk.kangaicodemother.langgraph4j.tools
 */
@SpringBootTest
class LogoGeneratorToolTest {

    @Resource
    private LogoGeneratorTool logoGeneratorTool;

    @Test
    void testGenerateLogos() {
        // 测试生成Logo
        List<ImageResource> logos = logoGeneratorTool.generateLogos("技术公司现代简约风格Logo");
        assertNotNull(logos);
        ImageResource firstLogo = logos.getFirst();
        assertEquals(ImageCategoryEnum.LOGO, firstLogo.getCategory());
        assertNotNull(firstLogo.getDescription());
        assertNotNull(firstLogo.getUrl());
        logos.forEach(logo ->
                System.out.println("Logo: " + logo.getDescription() + " - " + logo.getUrl())
        );
    }
}
