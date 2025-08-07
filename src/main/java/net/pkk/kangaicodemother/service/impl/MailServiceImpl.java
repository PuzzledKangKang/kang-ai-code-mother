package net.pkk.kangaicodemother.service.impl;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.pkk.kangaicodemother.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String Sender;

    private static final String SUBJECT = "VeriTrust邮箱验证码发送";

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public String sendSimpleMail(String to) {
        try {
            // 1. 初始化邮箱
            SimpleMailMessage message = new SimpleMailMessage();

            // 2. 设置发送方邮箱
            message.setFrom(Sender);

            // 3.设置接收方邮箱
            message.setTo(to.split(","));

            // 4.设置邮箱主题
            message.setSubject(SUBJECT);
            String secretCode = RandomUtil.randomNumbers(6);
            message.setText("验证码为：" + secretCode + "，请在2分钟内按页面提示提交验证码，您正在登录，若非本人操作，请勿泄露");

            javaMailSender.send(message);
            log.info("邮件发送成功: from={}, to={}, ", Sender, to);
            return secretCode;
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            return "邮件发送失败！";
        }
    }
}