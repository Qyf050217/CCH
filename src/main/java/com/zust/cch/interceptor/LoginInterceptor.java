package com.zust.cch.interceptor;

import com.zust.cch.common.Constants;
import com.zust.cch.utils.JwtUtil;
import com.zust.cch.utils.UserHolder;
import com.zust.cch.mapper.UserMapper;
import com.zust.cch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 从请求头拿到 Token
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        try {
            // 解析 Token 得到用户 id
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            // 通过 userId 获取 User 信息
            User user = userMapper.selectById(userId);

            // 将 User 对象存储到 ThreadLocal 中
            UserHolder.setUser(user);
            
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        UserHolder.remove();
    }
}