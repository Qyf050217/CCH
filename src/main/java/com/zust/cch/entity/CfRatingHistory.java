package com.zust.cch.entity;

import lombok.Data;
import java.util.Date;

@Data
public class CfRatingHistory {
    private Long id;
    private String cfHandle;
    private Integer contestId;
    private String contestName;
    private Integer rank;
    private Integer oldRating;
    private Integer newRating;
    private Integer ratingChange;
    private Date contestTime;
    private Date createTime;
}