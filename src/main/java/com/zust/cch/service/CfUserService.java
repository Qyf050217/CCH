package com.zust.cch.service;

import com.zust.cch.entity.CfUser;

public interface CfUserService {
    void checkCfUserExist(String cfHandle);
    CfUser insertCfUser(String cfHandle);
    CfUser getCfUserInfoByHandle(String handle);
}
