package com.zust.cch.service;

import com.zust.cch.entity.CfRatingHistory;
import com.zust.cch.entity.CfUser;
import java.util.List;

public interface CfUserService {
    // void checkCfUserExist(String cfHandle);
    CfUser insertCfUser(String cfHandle);
    CfUser getCfUserInfoByHandle(String handle);
    // List<CfRatingHistory> getHistoryFromAPI(String handle);
    // List<CfRatingHistory> getHistoryFromDb(String handle);

    List<CfRatingHistory> getHistory(String handle);
}
