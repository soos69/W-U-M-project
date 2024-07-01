package com.hnpl.wum.myPage.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoForm {

    @NotBlank(message="비밀번호를 입력해주세요")
    @Length(min=8,max = 16,message = "비밀번호는 8~16글자로 입력하세요")
    private String password;

    private String password2;

    private String nickname;

    @Email
    private String email;

    private String tel;

    private Long userSeq;
}
