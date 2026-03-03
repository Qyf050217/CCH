package com.zust.cch.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserFollowMapper {
    @Select("SELECT COUNT(*) FROM user_follow WHERE user_id = #{userId} AND cf_handle = #{cfHandle}")
    int checkFollowStatus(Integer userId, String cfHandle);

    @Insert("INSERT INTO user_follow(user_id, cf_handle) VALUES(#{userId}, #{cfHandle})")
    void insertFollow(Integer userId, String cfHandle);

    @Delete("DELETE FROM user_follow WHERE user_id = #{userId} AND cf_handle = #{cfHandle}")
    void deleteFollow(Integer userId, String cfHandle);
}