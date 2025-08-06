package net.pkk.kangaicodemother.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author 林子康
 * @since 2025/8/6
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
