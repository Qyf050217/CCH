package com.zust.cch.dto;

import jakarta.validation.constraints.NotBlank;

public record IdentityLoginDTO (
        @NotBlank(message = "账号或用户名不能为空")
        String identity,

        @NotBlank(message = "密码不能为空")
        String password
) {}
