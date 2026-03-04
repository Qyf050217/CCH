package com.zust.cch.entity;
import lombok.Data;

@Data
public class CfUser {
    private Integer id;
    private String cfHandle;
    private String avatar;
    private Integer currentRating;
    private Integer maxRating;
}
