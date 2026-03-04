package com.zust.cch.service;

import com.zust.cch.entity.CfUser;

public interface CfUserService {
    void checkCfUserExist(String cfHandle);
    void insertCfUser(String cfHandle);
}
