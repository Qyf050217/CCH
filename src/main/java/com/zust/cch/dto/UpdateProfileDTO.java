package com.zust.cch.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateProfileDTO(
        @NotBlank(message = "用户名不能为空")
        @Length(min = 2, max = 20, message = "用户名长度必须在 2 到 20 之间")
        String userName,

        @NotBlank(message = "Codeforces Handle 不能为空")
        String codeforcesName,

        @NotBlank(message = "请设置您的登录密码")
        @Length(min = 6, max = 20, message = "密码长度必须在 6 到 20 之间")
        String password
) {}