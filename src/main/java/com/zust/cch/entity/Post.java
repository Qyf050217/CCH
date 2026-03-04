package com.zust.cch.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Integer floor;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;
    private Integer isTop;
    private Integer status;
    private LocalDateTime createdAt;
}
