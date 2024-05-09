package com.app.demo.service;

import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.dto.request.LoginRequestDTO;
import com.app.demo.dto.response.LoginResponseDTO;
import com.app.demo.dto.response.TokenRefreshResponse;
import com.app.demo.entity.Member;

public interface MemberService {
    Member findUserById(Long userId);
    public Member signUp(MemberSignupDTO memberSignUpDto) throws Exception;
    public LoginResponseDTO.JwtToken login (LoginRequestDTO loginRequestDTO);
    void deleteMember(Member member);
    public TokenRefreshResponse refresh(String refreshToken);
}
