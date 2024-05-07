package com.app.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignupDTO {
    private String userID;
    private String password;
    private String nickname;
}
