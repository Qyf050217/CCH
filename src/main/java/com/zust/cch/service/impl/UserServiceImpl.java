package com.zust.cch.service.impl;

import com.zust.cch.dto.IdentityLoginDTO;
import com.zust.cch.entity.User;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.UserMapper;
import com.zust.cch.service.UserService;
import com.zust.cch.utils.JwtUtil;
import com.zust.cch.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

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

        return JwtUtil.genToken(user);
    }
}