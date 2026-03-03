package com.zust.cch.mapper;

import com.zust.cch.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE user_name = #{identity} OR mail = #{identity}")
    User selectByIdentity(String identity);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Integer id);

    @Insert("INSERT INTO user(user_name, password, mail, codeforces_name) VALUES(#{userName}, #{password}, #{mail}, #{codeforcesName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE user SET user_name = #{userName}, codeforces_name = #{codeforcesName}, password = #{password} WHERE id = #{id}")
    void updateUserProfile(Integer id, String userName, String codeforcesName, String password);
}
