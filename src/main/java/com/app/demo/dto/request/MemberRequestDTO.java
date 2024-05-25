package com.app.demo.dto.request;


import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class UpdateNicknameDTO{
        private Long memberId;
        private String newNickname;
    }

    @Getter
    public static class UpdatePasswdDTO{
        private Long memberId;
        private String newPasswd;
    }
}
