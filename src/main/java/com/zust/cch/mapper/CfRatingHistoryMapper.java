package com.zust.cch.mapper;

import com.zust.cch.entity.CfRatingHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CfRatingHistoryMapper {

    @Insert("INSERT IGNORE INTO cf_rating_history " +
            "(cf_handle, contest_id, contest_name, `rank`, old_rating, new_rating, rating_change, contest_time) " +
            "VALUES " +
            "(#{cfHandle}, #{contestId}, #{contestName}, #{rank}, #{oldRating}, #{newRating}, #{ratingChange}, #{contestTime})")
    void insertIgnore(CfRatingHistory history);

    // 查询某个用户的所有比赛历史（按时间倒序）
    @Select("SELECT * FROM cf_rating_history WHERE cf_handle = #{handle} ORDER BY contest_time DESC")
    List<CfRatingHistory> selectHistoryByHandle(String handle);
}
