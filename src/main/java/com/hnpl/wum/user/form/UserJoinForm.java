package com.hnpl.wum.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserJoinForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;


    private String year;
    private String month;
    private String day;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String tel;
}
