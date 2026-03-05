package com.zust.cch.controller;

import com.zust.cch.common.Constants;
import com.zust.cch.common.Result;
import com.zust.cch.dto.IdentityLoginDTO;
import com.zust.cch.dto.MailAuthDTO;
import com.zust.cch.dto.UpdateProfileDTO;
import com.zust.cch.entity.User;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.service.UserService;
import com.zust.cch.utils.JwtUtil;
import com.zust.cch.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody IdentityLoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result<String> emailLogin(@Validated @RequestBody MailAuthDTO authDTO) {
        String token = userService.loginOrRegisterByMail(authDTO);
        return Result.success(token);
    }


    @PostMapping("/update-profile")
    public Result<Void> updateProfile(
            @Validated @RequestBody UpdateProfileDTO profileDTO,
            HttpServletRequest request) {

        String token = request.getHeader(Constants.TOKEN_HEADER);
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");
            userService.updateProfile(userId, profileDTO);
            return Result.success();
        } catch (Exception e) {
            return Result.error(401, "登录身份验证失败，请重新登录");
        }
    }

    @GetMapping("/profile/{id}")
    public Result<UserVO> userInfoById(@PathVariable Integer id) {
        UserVO user = userService.userInfoById(id);
        return Result.success(user);
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");
            User user = userService.findById(userId);
            user.setPassword("");
            return Result.success(user);
        } catch (Exception e) {
            throw new BusinessException(401, "登录已失效");
        }
    }
}
