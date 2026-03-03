package com.zust.cch.utils;

public class UserHolder {
    private static final ThreadLocal<Integer> USER = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        USER.set(userId);
    }

    public static Integer getUserId() {
        return USER.get();
    }

    public static void remove() {
        USER.remove();
    }
}