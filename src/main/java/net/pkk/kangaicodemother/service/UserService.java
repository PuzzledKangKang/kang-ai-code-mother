package net.pkk.kangaicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import net.pkk.kangaicodemother.common.BaseResponse;
import net.pkk.kangaicodemother.model.dto.user.UserLoginByEmailAndCodeRequest;
import net.pkk.kangaicodemother.model.dto.user.UserQueryRequest;
import net.pkk.kangaicodemother.model.entity.User;
import net.pkk.kangaicodemother.model.vo.LoginUserVO;
import net.pkk.kangaicodemother.model.vo.UserVO;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author 林子康
 * @since 1.0.1
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return 脱敏的已登录用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request Http请求，用于存放用户信息到 Session
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request Http请求，用于存放用户信息到 Session
     * @return 注销是否成功
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户信息列表（分页）
     *
     * @param userList 多个用户信息
     * @return 多个脱敏后的用户信息
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据查询条件构造查询参数
     *
     * @param userQueryRequest 用户查询信息
     * @return 查询信息
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 获取当前登录用户
     *
     * @param request Http请求，用于获取当前 Session 中登录用户的信息
     * @return 当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 加密
     * @param userPassword 用户密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 发送短信验证码给对应用户
     * @param phone
     * @return
     */
    BaseResponse<String> sendCodeToUser(String phone);

    /**
     * 手机号码和验证码登录
     *
     * @param userLoginByPhoneAndCode
     * @return
     */
    LoginUserVO loginByEmailAndVerifyCode(UserLoginByEmailAndCodeRequest userLoginByPhoneAndCode, HttpServletRequest request);

}
