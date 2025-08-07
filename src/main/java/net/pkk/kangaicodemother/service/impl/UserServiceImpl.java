package net.pkk.kangaicodemother.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.pkk.kangaicodemother.common.BaseResponse;
import net.pkk.kangaicodemother.common.ResultUtils;
import net.pkk.kangaicodemother.exception.BusinessException;
import net.pkk.kangaicodemother.exception.ErrorCode;
import net.pkk.kangaicodemother.model.dto.UserLoginByEmailAndCodeRequest;
import net.pkk.kangaicodemother.model.dto.UserQueryRequest;
import net.pkk.kangaicodemother.model.entity.User;
import net.pkk.kangaicodemother.mapper.UserMapper;
import net.pkk.kangaicodemother.model.enums.UserRoleEnum;
import net.pkk.kangaicodemother.model.vo.LoginUserVO;
import net.pkk.kangaicodemother.model.vo.UserVO;
import net.pkk.kangaicodemother.service.MailService;
import net.pkk.kangaicodemother.service.UserService;
import net.pkk.kangaicodemother.utils.RegexUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.pkk.kangaicodemother.constant.RedisConstants.*;
import static net.pkk.kangaicodemother.constant.UserConstant.DEFAULT_ROLE;
import static net.pkk.kangaicodemother.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户 服务层实现。
 *
 * @author 林子康
 * @since 1.0.1
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {

    @Resource
    private MailService mailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2. 检查是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 3. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 4. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 4. 获得脱敏后的用户信息
        return this.getLoginUserVO(user);
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id", id)
                .eq("user_role", userRole)
                .like("user_account", userAccount)
                .like("user_name", userName)
                .like("user_profile", userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接返回上述结果）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "kangkang";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }

    @Override
    public BaseResponse<String> sendCodeToUser(String email) {
        // 1.校验手机号码
        if (RegexUtils.isEmailInvalid(email)) {
            // 2.手机号码不符合将抛出异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号码不符合规范");
        }

        // 3.根据电子邮箱信息发送对应验证码
        String verifyCode = mailService.sendSimpleMail(email);

        // 4.保存验证码到 redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, verifyCode, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 5.发送验证码
        log.info("发送短信验证码成功，验证码：{}", verifyCode);

        return ResultUtils.success(verifyCode);
    }

    @Override
    @Transactional
    public LoginUserVO loginByEmailAndVerifyCode(UserLoginByEmailAndCodeRequest userLoginByEmailAndCode, HttpServletRequest request) {
        String email = userLoginByEmailAndCode.getEmail();
        if (email == null || RegexUtils.isEmailInvalid(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱号码有误");
        }
        // 3.从 redis 中获取校验验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        // 4.拿到用户输入的验证码
        String userInputCode = userLoginByEmailAndCode.getVerifyCode();
        // 5.检查验证码的合理性和是否正确
        if (cacheCode == null || !cacheCode.equals(userInputCode)) {
            // 验证码有问题直接抛异常
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "验证码为空或输入有误");
        }
        // 6.一致，根据手机号查询用户 select * from tb_user where email = ? 只查询到一位唯一的用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", email);
        User user = this.mapper.selectOneByQuery(queryWrapper);

        // 7.判断手机号码是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            user = createUserWithEmail(email);
        }

        // 7.保存用户信息到 redis 中
        // 7.1 随机生成一个 token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2 将 User 对象转换为 Hash 存储
        LoginUserVO loginUserVO = BeanUtil.copyProperties(user, LoginUserVO.class);
        if (loginUserVO == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新用户注册失败！");
        }
        // 使用 BeanUtil 将 loginUserVO 转化成 HashMap
        Map<String, Object> userMap = null;
        try {
            // 封装 vo 转 map 的方法写在类里面了
            userMap = LoginUserVO.voToMap(loginUserVO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 将 LoginUser 信息存入 redis 中
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
//        // 7.3 将对象存储
//        String tokenKey = LOGIN_USER_KEY + token;
//        // 以哈希形式存储到 redis 中
//        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
//        // 7.4 设置 token 的有效期
//        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //最后返回用户信息
        System.out.println(user);
        return this.getLoginUserVO(user);
    }

    private User createUserWithEmail(String email) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", email);
        User alreadyRegister = this.getOne(queryWrapper);
        // 判断邮箱号码是否已经注册过了
        if (BeanUtil.isNotEmpty(alreadyRegister)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "邮箱已被注册！");
        }

        // 创建用户（感觉可以用建造者模式来做）
        User user = new User();
        // ！设置手机号码
        user.setEmail(email);
        user.setUserRole(DEFAULT_ROLE);
        // 2.保存用户信息
        boolean save = this.save(user);
        if (save) {
            return user;
        }
        return null;
    }


}
