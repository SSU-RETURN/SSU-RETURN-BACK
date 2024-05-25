package com.app.demo.dto.response;


import lombok.*;

public class LoginResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OAuthResponse {
        Long memberId;
        String nickname;
        String accessToken;
        String refreshToken;
        Boolean isLogin;
        Integer isPreference;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class JwtToken {
        String grantType;
        String accessToken;
        String refreshToken;
    }
}
