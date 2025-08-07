package net.pkk.kangaicodemother.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 林子康
 * @since 2025/8/6
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public static Map<String, Object> voToMap(LoginUserVO loginUserVO) {
        if (loginUserVO == null) {
            return null;
        }
        Map<String, Object> userMap = BeanUtil.beanToMap(loginUserVO, new HashMap<>(),
                // 属性拷贝选项，创建并忽略对象中为空的属性，设置 变量名 / 值  -> key / value
                CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((fieldName, fieldValue) -> {
                    if (fieldValue == null) {
                        return null;
                    }
                    return fieldValue.toString();
                }));
        return userMap;
    }

    public static LoginUserVO mapToLoginUser(Map<String, Object> userMap) {
        if (userMap.isEmpty()) {
            return null;
        }
        return BeanUtil.copyProperties(userMap, LoginUserVO.class);
    }
}
