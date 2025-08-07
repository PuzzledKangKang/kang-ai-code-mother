package net.pkk.kangaicodemother.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * UserLoginByPhoneAndCode 类 用于：封装 phone 和 verifyCode 的一个请求体
 *
 * @author 林子康
 * @since 2025/3/8
 */
@Data
public class UserLoginByEmailAndCodeRequest implements Serializable {

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 短信验证码
     */
    private String verifyCode;

    private static final long serialVersionUID = 7574209058044818313L;

}