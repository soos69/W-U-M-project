package com.hnpl.wum.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWishDto {
    private Long wishSeq;
    private Long userSeq;
    private Long movieSeq;
    private String movieName;
    private String poster;
    private List<UserWishDto> userWishDtoList;
}
