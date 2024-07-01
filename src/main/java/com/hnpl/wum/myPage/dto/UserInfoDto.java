package com.hnpl.wum.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long userSeq;
    private String name;
    private LocalDate birth;
    private String id;
    private String nickname;
    private String password;
    private String tel;
    private String email;
    private LocalDate regDate;
}
