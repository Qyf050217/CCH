package com.zust.cch.mapper;

import com.zust.cch.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据 用户名或邮箱  查询用户
     */
    @Select("SELECT * FROM user WHERE user_name = #{identity} OR mail = #{identity}")
    User selectByIdentity(String identity);


    /**
     * 新增用户
     */
    @Insert("INSERT INTO user(user_name, password, mail, codeforces_name) VALUES(#{userName}, #{password}, #{mail}, #{codeforcesName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);
}
