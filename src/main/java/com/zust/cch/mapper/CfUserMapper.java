package com.zust.cch.mapper;

import com.zust.cch.entity.CfUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CfUserMapper {
    @Insert("INSERT INTO cf_user(cf_handle, avatar,current_rating) VALUES(#{cfHandle}, #{avatar}, #{currentRating})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCfUser(CfUser cfUser);

    @Select("SELECT * FROM cf_user WHERE cf_handle = #{cfHandle}")
    CfUser selectByHandle(String cfHandle);
}
