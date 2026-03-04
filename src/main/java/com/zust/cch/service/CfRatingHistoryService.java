package com.zust.cch.service;

import com.zust.cch.entity.CfRatingHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CfRatingHistoryService {
    void saveHistoryList(List<CfRatingHistory> historyList);
}
