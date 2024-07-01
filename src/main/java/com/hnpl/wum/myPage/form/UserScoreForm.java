package com.hnpl.wum.myPage.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreForm {

    @NotBlank(message = "리뷰내용을 적어주세요")
    private String reviewContent;

    @NotNull(message = "평점을 선택해주세요")
    private Integer score;

    private String movieName;

    private Integer reviewLike;

    private String postDate;

    private Long scoreSeq;

    private Long userSeq;
}
