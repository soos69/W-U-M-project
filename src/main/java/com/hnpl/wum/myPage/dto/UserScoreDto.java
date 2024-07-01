package com.hnpl.wum.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreDto {

    private Long scoreSeq;
    private Long userSeq;
    private Long movieSeq;
    private String poster;
    private String movieName;
    private Integer score;
    private String reviewContent;
    private Integer reviewLike;
    private String postDate;
}
