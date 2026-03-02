package com.zust.cch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.zust.cch.common.Constants.sendMailBot;

@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本邮件
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendMailBot);           // 发件人（需与配置中的 username 一致）
        message.setTo(to);                      // 收件人
        message.setSubject(subject);            // 邮件主题
        message.setText(content);               // 邮件内容
        mailSender.send(message);
    }
}
