package com.zust.cch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.zust.cch.common.Constants.passwordMaxLen;
import static com.zust.cch.common.Constants.passwordMinLen;

public record IdentityLoginDTO (
        @NotBlank(message = "账号或用户名不能为空")
        String identity,

        @NotBlank(message = "密码不能为空")
        @Size(min = passwordMinLen, max = passwordMaxLen, message = "密码长度在 {min} 到 {max} 之间")
        String password
) {}
