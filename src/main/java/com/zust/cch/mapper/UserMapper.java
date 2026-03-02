package com.zust.cch.mapper;

import com.zust.cch.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /**
     * 根据 用户名或邮箱  查询用户
     */
    @Select("SELECT * FROM user WHERE user_name = #{identity} OR mail = #{identity}")
    User selectByIdentity(String identity);


    /**
     * 根据 id 查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Integer id);


    /**
     * 新增用户
     */
    @Insert("INSERT INTO user(user_name, password, mail, codeforces_name) VALUES(#{userName}, #{password}, #{mail}, #{codeforcesName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    /**
     * 更新用户
     */
    @Update("UPDATE user SET user_name = #{userName}, codeforces_name = #{codeforcesName}, password = #{password} WHERE id = #{id}")
    void updateUserProfile(Integer id, String userName, String codeforcesName, String password);
}
