package com.zust.cch.controller;

import cn.hutool.core.util.RandomUtil;
import com.zust.cch.common.Constants;
import com.zust.cch.common.Result;
import com.zust.cch.dto.SendCodeDTO;
import com.zust.cch.service.impl.MailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/mail")
public class MailController {
    private static final Logger log = LoggerFactory.getLogger(MailController.class);
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/send-code")
    public Result<Void> sendCode(@Validated @RequestBody SendCodeDTO sendCodeDTO) {
        String code = RandomUtil.randomNumbers(6);
        String mail = sendCodeDTO.mail();
        String redisKey = Constants.REDIS_KEY_MAIL_CODE + mail;
        redisTemplate.opsForValue().set(
                redisKey,
                code,
                Constants.MAIL_CODE_EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );

        try {
            mailService.sendSimpleMail(mail, "【CF Contest Helper】验证码", code);
            log.info("验证码成功发送给：{}, 验证码为：{}", mail, code);
        } catch (Exception e) {
            redisTemplate.delete(redisKey);
            log.error("邮件发送失败，目标邮件为：{}", mail, e);
            throw new RuntimeException("邮件发送失败，请稍后重试");
        }
        return Result.success();
    }
}
