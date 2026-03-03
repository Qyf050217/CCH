package com.zust.cch.entity;
import lombok.Data;
import java.time.LocalTime;

@Data
public class CFUser {
    private Integer id;
    private String cfHandle;
    private Integer currentRating;
    private LocalTime updateTime;
}
