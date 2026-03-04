package com.zust.cch.controller;

import com.zust.cch.common.Result;
import com.zust.cch.entity.CfUser;
import com.zust.cch.service.CfUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/cf-user")
public class CfUserController {
    @Autowired
    private CfUserService cfUserService;

    // 数据库里存的cf用户信息
    @GetMapping("/infoDb/{handle}")
    public Result<CfUser> cfDbInfo(@PathVariable String handle) {
        CfUser cfUser = cfUserService.getCfUserInfoByHandle(handle);
        return Result.success(cfUser);
    }

    // api查cf用户信息
    @GetMapping("/infoWeb/{handle}")
    public Result<CfUser> cfWebInfo(@PathVariable String handle) {
        CfUser cfUser = cfUserService.insertCfUser(handle);
        return Result.success(cfUser);
    }

    // 比赛信息
    @GetMapping("/contest/{handle}")
    public Result<List<Map<String, Object>>> getCfUserRatingContestHistory(@PathVariable String handle) {
        List<Map<String, Object>> history = cfUserService.getCfUserRatingContestHistory(handle);
        return Result.success(history);
    }
}
