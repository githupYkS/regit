package com.yks.regit.util;

import com.yks.regit.emtity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

//@Configuration
public class SetMail {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Resource
    private JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String sendMail(String mail){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            redisUtil.set(mail, checkCode, 1);
            message.setFrom("2873717298@qq.com");
            message.setTo(mail);
            message.setSubject("欢迎成为宠物救助平台的用户");
            message.setText("您的注册验证码为：" + checkCode);

            javaMailSender.send(message);
            logger.info("邮件发送成功");
        } catch (MailSendException e) {
            logger.error("目标邮箱不存在");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文本邮件发送异常");
        }
        return checkCode;
    }

    public User addLoginTicket(User user){
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*30);
        user.setAvatar(UUID.randomUUID().toString().replaceAll("-",""));

        String user_tooken = user.getAvatar();
        redisUtil.set("user_tooken", user_tooken);

        return user;

    }
}
