package com.zust.cch.interceptor;

import com.zust.cch.common.Constants;
import com.zust.cch.utils.JwtUtil;
import com.zust.cch.utils.UserHolder;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 从请求头拿到 Token
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");
            UserHolder.setUserId(userId);
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