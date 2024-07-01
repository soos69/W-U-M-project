package com.hnpl.wum.require.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireForm {

    @NotBlank(message = "제목을 적어주세요")
    private String title;

    @NotBlank(message = "내용을 적어주세요")
    private String content;

    private String postDate;

    private Integer requireLike;

    private Long userSeq;

    private Long requireSeq;
}
