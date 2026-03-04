package com.zust.cch.service.impl;

import com.zust.cch.entity.CfRatingHistory;
import com.zust.cch.mapper.CfRatingHistoryMapper;
import com.zust.cch.service.CfRatingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CfRatingHistoryServiceImpl implements CfRatingHistoryService {

    @Autowired
    private CfRatingHistoryMapper ratingHistoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHistoryList(List<CfRatingHistory> historyList) {
        if (historyList == null || historyList.isEmpty()) {
            return;
        }

        for (CfRatingHistory history : historyList) {
            ratingHistoryMapper.insertIgnore(history);
        }
    }


}