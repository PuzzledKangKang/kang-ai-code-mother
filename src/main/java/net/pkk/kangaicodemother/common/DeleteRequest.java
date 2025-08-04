package net.pkk.kangaicodemother.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求包装类
 * 
 * @Author: 林子康
 * @date: 2025/8/4
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
