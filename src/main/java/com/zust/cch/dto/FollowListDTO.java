package com.zust.cch.dto;
import jakarta.validation.constraints.NotNull;

public record FollowListDTO(
        @NotNull(message = "用户名不为空")
        Integer userId
) {}
