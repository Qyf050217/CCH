package com.zust.cch.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String userName;
    private String codeforcesName;
    private String mail;
    private Boolean isFollowed;
}