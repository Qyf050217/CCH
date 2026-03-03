package com.zust.cch.service.impl;

import com.zust.cch.entity.CfUser;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.CfUserMapper;
import com.zust.cch.mapper.UserFollowMapper;
import com.zust.cch.service.CfUserService;
import com.zust.cch.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;
    @Autowired
    private CfUserService cfUserService;
    @Autowired
    private CfUserMapper cfUserMapper;

    @Override
    public void follow(Integer userId, String cfHandle) {
        if (userFollowMapper.checkFollowStatus(userId, cfHandle) > 0) {
            throw new BusinessException(400, "已经关注过该账号了");
        }
        cfUserService.insertCfUser(cfHandle);
        userFollowMapper.insertFollow(userId, cfHandle);
    }

    @Override
    public void unfollow(Integer userId, String cfHandle) {
        if (userFollowMapper.checkFollowStatus(userId, cfHandle) == 0) {
            throw new BusinessException(400, "未关注此账号");
        }
        userFollowMapper.deleteFollow(userId, cfHandle);
    }

    @Override
    public List<CfUser> getFollowList(Integer userId) {
        return cfUserMapper.selectFollowListByUserId(userId);
    }
}