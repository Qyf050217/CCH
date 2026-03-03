package com.zust.cch.entity;

import lombok.Data;
import java.util.Date;

@Data
public class UserFollow {
    // 该表的主键
    private Integer id;

    // 1
    private Integer userId;
    // jiangly
    private String cfHandle;
    private Date createTime;
}