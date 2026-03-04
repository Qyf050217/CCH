package com.zust.cch.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zust.cch.entity.CfUser;
import com.zust.cch.exception.BusinessException;
import com.zust.cch.mapper.CfUserMapper;
import com.zust.cch.service.CfUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zust.cch.common.Constants.API_Codeforces_UserContest;
import static com.zust.cch.common.Constants.API_Codeforces_UserExist;

@Slf4j
@Service
public class CfUserServiceImpl implements CfUserService {
    @Autowired
    private CfUserMapper cfUserMapper;

//    @Override
//    public void checkCfUserExist(String cfHandle) {
//        String url = API_Codeforces_UserExist + cfHandle;
//        try {
//            String responseStr = HttpUtil.get(url, 5000);
//            JSONObject json = JSONUtil.parseObj(responseStr);
//            if (!"OK".equals(json.getStr("status"))) {
//                throw new BusinessException(400, "该 Codeforces 账号不存在，请检查拼写");
//            }
//        } catch (Exception e) {
//            log.error("调用 CF API 失败", e);
//            throw new BusinessException(500, "连接 Codeforces 服务器超时，请稍后再试");
//        }
//    }

    @Override
    public CfUser insertCfUser(String cfHandle) {
        CfUser existUser = cfUserMapper.selectByHandle(cfHandle);
        if (existUser != null) {
            log.info("用户 {} 已存在", cfHandle);
            return existUser;
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
        return cfUser;
    }

    @Override
    public CfUser getCfUserInfoByHandle(String handle) {
        CfUser cfUser = cfUserMapper.selectByHandle(handle);
        if (cfUser == null) {
            throw new BusinessException("该用户不存在");
        }
        return cfUser;
    }

    @Override
    public List<Map<String, Object>> getCfUserRatingContestHistory(String handle) {
        String url = API_Codeforces_UserContest + handle;
        try {
            String response = HttpUtil.get(url, 10000);
            JSONObject json = JSONUtil.parseObj(response);
            if (!"OK".equals(json.getStr("status"))) {
                throw new BusinessException("获取比赛历史失败");
            }

            JSONArray resultArr = json.getJSONArray("result");
            List<Map<String, Object>> historyList = new ArrayList<>();

            for (int i = resultArr.size() - 1; i >= 0; i--) {
                JSONObject match = resultArr.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                map.put("contestId", match.getInt("contestId"));
                map.put("contestName", match.getStr("contestName"));
                map.put("rank", match.getInt("rank"));
                map.put("oldRating", match.getInt("oldRating"));
                map.put("newRating", match.getInt("newRating"));
                map.put("change", match.getInt("newRating") - match.getInt("oldRating"));
                map.put("updateTime", match.getLong("ratingUpdateTimeSeconds") * 1000);
                historyList.add(map);
            }
            return historyList;
        } catch (Exception e) {
            throw new BusinessException("请求 Codeforces API 超时");
        }
    }
}
