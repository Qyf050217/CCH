package com.zust.cch.entity;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String mail;
    private String codeforcesName;
}
