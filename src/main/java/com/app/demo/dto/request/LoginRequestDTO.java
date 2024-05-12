package com.app.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userID;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
