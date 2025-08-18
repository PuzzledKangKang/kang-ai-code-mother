package net.pkk.kangaicodemother.ai.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流式消息响应基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamMessage {

    /**
     * 消息类型
     */
    private String type;
}
