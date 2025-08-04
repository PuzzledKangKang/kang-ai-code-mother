package net.pkk.kangaicodemother.common;

import lombok.Data;
import net.pkk.kangaicodemother.exception.ErrorCode;

import java.io.Serializable;

/**
 * 通用响应类
 *
 * @param <T>
 * @author 林子康
 * @date 2025/8/4
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
