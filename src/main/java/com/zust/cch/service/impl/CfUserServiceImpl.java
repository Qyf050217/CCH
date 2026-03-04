package com.zust.cch.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zust.cch.entity.CfUser;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.CfUserMapper;
import com.zust.cch.service.CfUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zust.cch.common.Constants.API_Codeforces_UserExist;

@Slf4j
@Service
public class CfUserServiceImpl implements CfUserService {
    @Autowired
    private CfUserMapper cfUserMapper;

    @Override
    public void checkCfUserExist(String cfHandle) {
        String url = API_Codeforces_UserExist + cfHandle;
        try {
            String responseStr = HttpUtil.get(url, 5000);
            JSONObject json = JSONUtil.parseObj(responseStr);
            if (!"OK".equals(json.getStr("status"))) {
                throw new BusinessException(400, "该 Codeforces 账号不存在，请检查拼写");
            }
        } catch (Exception e) {
            log.error("调用 CF API 失败", e);
            throw new BusinessException(500, "连接 Codeforces 服务器超时，请稍后再试");
        }
    }

    @Override
    public void insertCfUser(String cfHandle) {
        CfUser existUser = cfUserMapper.selectByHandle(cfHandle);
        if (existUser != null) {
            log.info("用户 {} 已存在", cfHandle);
            return;
        }

        String url = API_Codeforces_UserExist + cfHandle;
        String responseStr = HttpUtil.get(url, 5000);
        JSONObject json = JSONUtil.parseObj(responseStr);
        if (!"OK".equals(json.getStr("status"))) {
            throw new BusinessException(400, "该 Codeforces 账号不存在，请检查拼写");
        }
        JSONObject userInfo = json.getJSONArray("result").getJSONObject(0);
        CfUser cfUser = new CfUser();
        cfUser.setCfHandle(userInfo.getStr("handle"));
        cfUser.setAvatar(userInfo.getStr("avatar"));
        cfUser.setCurrentRating(userInfo.getInt("rating", 0));
        cfUser.setMaxRating(userInfo.getInt("maxRating", 0));
        cfUserMapper.insertCfUser(cfUser);
    }

}
