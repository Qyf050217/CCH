package com.zust.cch.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.UserFollowMapper;
import com.zust.cch.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zust.cch.common.Constants.API_Codeforces_UserExist;

@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public void follow(Integer userId, String cfHandle) {
        if (userFollowMapper.checkFollowStatus(userId, cfHandle) > 0) {
            throw new BusinessException(400, "您已经关注过该账号了");
        }
        String url = API_Codeforces_UserExist + cfHandle;
        try {
            String responseStr = HttpUtil.get(url, 5000);
            JSONObject json = JSONUtil.parseObj(responseStr);
            if (!"OK".equals(json.getStr("status"))) {
                throw new BusinessException(400, "该 Codeforces 账号不存在，请检查拼写");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用 CF API 失败", e);
            throw new BusinessException(500, "连接 Codeforces 服务器超时，请稍后再试");
        }
        userFollowMapper.insertFollow(userId, cfHandle);
    }

    @Override
    public void unfollow(Integer userId, String cfHandle) {
        userFollowMapper.deleteFollow(userId, cfHandle);
    }
}