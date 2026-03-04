package com.zust.cch.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer id;
    private Integer userId;
    private String title;
    private Integer postId;
    private String content;
    private Integer floor;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createdAt;
}
