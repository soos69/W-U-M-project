package com.hnpl.wum.myPage.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequireForm {

    @NotBlank(message = "제목을 적어주세요")
    private String title;

    @NotBlank(message = "내용을 적어주세요")
    private String content;

    private String postDate;

    private Integer requireLike;

    private Long userSeq;

    private Long requireSeq;
}
