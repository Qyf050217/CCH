package com.zust.cch.service;

import com.zust.cch.entity.CfUser;

import java.util.List;
import java.util.Map;

public interface CfUserService {
    // void checkCfUserExist(String cfHandle);
    CfUser insertCfUser(String cfHandle);
    CfUser getCfUserInfoByHandle(String handle);
    List<Map<String, Object>> getCfUserRatingContestHistory(String handle);
}
