package com.hnpl.wum.require.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireDto {
    private Long requireSeq;
    private Long userSeq;
    private String nickname;
    private String title;
    private String content;
    private String postDate;
    private Integer requireLike;
    private Long likeSeq;
    private List<RequireDto> userRequireDtoList;
}
