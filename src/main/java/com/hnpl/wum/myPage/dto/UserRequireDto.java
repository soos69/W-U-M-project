package com.hnpl.wum.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequireDto {
    private Long requireSeq;
    private Long userSeq;
    private String title;
    private String content;
    private String postDate;
    private Integer requireLike;
    private List<UserRequireDto> userRequireDtoList;
}
