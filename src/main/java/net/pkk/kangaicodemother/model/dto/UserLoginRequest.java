package net.pkk.kangaicodemother.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author 林子康
 * @since 2025/8/6
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -5790431970636724102L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
