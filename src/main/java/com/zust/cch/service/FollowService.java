package com.zust.cch.service;

import com.zust.cch.entity.CfUser;

import java.util.List;

public interface FollowService {
    void follow(Integer userId, String cfHandle);
    void unfollow(Integer userId, String cfHandle);
    List<CfUser> getFollowList(Integer userId);
}