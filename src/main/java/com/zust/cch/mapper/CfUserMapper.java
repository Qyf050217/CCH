package com.zust.cch.mapper;

import com.zust.cch.entity.CfUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface CfUserMapper {
    @Insert("INSERT INTO cf_user(cf_handle, avatar,current_rating) VALUES(#{cfHandle}, #{avatar}, #{currentRating})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCfUser(CfUser cfUser);
}
