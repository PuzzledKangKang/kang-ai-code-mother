package net.pkk.kangaicodemother.ai;

import dev.langchain4j.service.SystemMessage;
import net.pkk.kangaicodemother.model.enums.CodeGenTypeEnum;

/**
 * AI代码生成类型智能路由服务
 * 使用结构化输出直接返回枚举类型
 *
 * @author 林子康
 * @version 1.0
 * @since 2025/8/20 10:12
 */
public interface AiCodeGenTypeRoutingService {

    /**
     * 根据用户需求智能选择代码生成类型
     *
     * @param userPrompt 用户输入的需求描述
     * @return 推荐的代码生成类型
     */
    @SystemMessage(fromResource = "prompt/codegen-routing-system-prompt.txt")
    CodeGenTypeEnum routeCodeGenType(String userPrompt);
}
