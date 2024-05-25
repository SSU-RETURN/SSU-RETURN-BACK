package com.app.demo.converter;

import com.app.demo.dto.response.LoginResponseDTO;
import com.app.demo.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public LoginResponseDTO.OAuthResponse convertToOAuthResponse(Member member, String accessToken, String refreshToken, Integer isPreference) {
        return LoginResponseDTO.OAuthResponse.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isPreference(isPreference)
                .isLogin(true)
                .build();
    }
}
