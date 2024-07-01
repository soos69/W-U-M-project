package com.hnpl.wum.user.dto;

import com.hnpl.wum.user.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userSeq;

    private String id;

    private String password;

    private String nickname;

    private String name;

    private LocalDate birth;

    private String tel;

    private String email;

    private Role role;

    private LocalDate regDate;
}
