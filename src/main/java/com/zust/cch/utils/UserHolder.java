package com.zust.cch.utils;

import com.zust.cch.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> USER = new ThreadLocal<>();

    // 设置用户对象
    public static void setUser(User user) { USER.set(user); }

    // 获取用户ID
    public static Integer getUserId() {
        User user = USER.get();
        return (user != null) ? user.getId() : null;
    }

    // 获取用户名
    public static String getUserName() {
        User user = USER.get();
        return (user != null) ? user.getUserName() : null;
    }

    // 获取用户对象
    public static User getUser() {
        return USER.get();
    }

    // 删除当前线程的用户对象
    public static void remove() { USER.remove(); }
}