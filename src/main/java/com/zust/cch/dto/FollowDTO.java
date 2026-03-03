package com.zust.cch.dto;

import jakarta.validation.constraints.NotBlank;

public record FollowDTO(
        @NotBlank(message = "Codeforces Handle 不能为空")
        String cfHandle
) {}