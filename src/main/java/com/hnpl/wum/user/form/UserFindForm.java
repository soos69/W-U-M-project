package com.hnpl.wum.user.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserFindForm {


    private String name;

    private String tel;

    private String id;

    private String password;


}
