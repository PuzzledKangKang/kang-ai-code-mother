package net.pkk.kangaicodemother.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.pkk.kangaicodemother.common.PageRequest;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author 林子康
 * @since 2025/8/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
