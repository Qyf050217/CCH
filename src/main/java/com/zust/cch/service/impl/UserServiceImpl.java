package com.zust.cch.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zust.cch.common.Constants;
import com.zust.cch.dto.IdentityLoginDTO;
import com.zust.cch.dto.MailAuthDTO;
import com.zust.cch.entity.User;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.UserMapper;
import com.zust.cch.service.UserService;
import com.zust.cch.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.zust.cch.utils.JwtUtil.genToken;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     */
    @Override
    public String login(IdentityLoginDTO loginDTO) {
        String identity = loginDTO.identity();
        String code = loginDTO.password();

        User user = userMapper.selectByIdentity(identity);

        if (user == null) {
            throw new BusinessException("用户名或密码输入错误");
        }
        String encode = MD5Utils.code(code);
        if (encode == null || !encode.equals(user.getPassword())) {
            throw new BusinessException("用户名或密码输入错误");
        }

        return genToken(user);
    }

    /**
     * 邮箱注册或登录
     */
    @Override
    public String loginOrRegisterByMail(MailAuthDTO authDTO) {
        String mail = authDTO.mail();
        String code = authDTO.code();
        String redisKey = Constants.REDIS_KEY_MAIL_CODE + mail;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null || !cachedCode.equals(code)) {
            throw new BusinessException("验证码错误或过期");
        }
        redisTemplate.delete(redisKey);
        User user = userMapper.selectByIdentity(mail);

        if (user == null) { // 注册
            user = new User();
            user.setMail(mail);
            user.setUserName(Constants.TEMP_USER_PREFIX + RandomUtil.randomString(6));
            user.setPassword(MD5Utils.code(Constants.DEFAULT_USER_PASSWORD));
            user.setCodeforcesName(Constants.DEFAULT_CF_NAME);
            userMapper.insertUser(user);
        }

        return genToken(user);
    }
}