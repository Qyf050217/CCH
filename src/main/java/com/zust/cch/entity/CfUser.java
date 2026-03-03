package com.zust.cch.entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CfUser {
    private Integer id;
    private String cfHandle;
    private String avatar;
    private Integer currentRating;
}
