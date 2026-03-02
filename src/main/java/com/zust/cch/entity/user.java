package com.zust.cch.entity;
import lombok.Data;

import java.time.LocalTime;

@Data
public class user {
    private String userName;
    private String password;
    private String mail;
    private String codeforcesName;
    private LocalTime createTime;
    private LocalTime updateTime;
}
