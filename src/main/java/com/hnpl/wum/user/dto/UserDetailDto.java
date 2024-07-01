package com.hnpl.wum.user.dto;


import com.hnpl.wum.user.constant.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailDto {

    private Long num;

    private Long cnt;

    private Long userSeq;

    private Long seq;

    private String id;

    private String password;

    private String nickname;

    private String name;

    private String tel;

    private LocalDate regDate;
}
