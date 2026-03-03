package com.zust.cch.controller;

import com.zust.cch.common.Result;
import com.zust.cch.dto.FollowDTO;
import com.zust.cch.service.FollowService;
import com.zust.cch.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/add")
    public Result<Void> follow(@Validated @RequestBody FollowDTO followDTO) {
        Integer userId = UserHolder.getUserId();
        String cfHandle = followDTO.cfHandle();
        followService.follow(userId, cfHandle);
        return Result.success();
    }

    @PostMapping("/remove")
    public Result<Void> unfollow(@Validated @RequestBody FollowDTO followDTO) {
        Integer userId = UserHolder.getUserId();
        String cfHandle = followDTO.cfHandle();
        followService.unfollow(userId, cfHandle);
        return Result.success();
    }
}
