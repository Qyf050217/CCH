package com.zust.cch.service;

public interface FollowService {
    void follow(Integer userId, String cfHandle);
    void unfollow(Integer userId, String cfHandle);
}