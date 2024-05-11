package com.app.demo.service.impl;

import com.app.demo.apiPayload.code.status.ErrorStatus;
import com.app.demo.apiPayload.exception.AuthException;
import com.app.demo.apiPayload.exception.UserException;
import com.app.demo.converter.MemberConverter;
import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.dto.request.LoginRequestDTO;
import com.app.demo.dto.response.LoginResponseDTO;
import com.app.demo.dto.response.TokenRefreshResponse;
import com.app.demo.entity.Member;
import com.app.demo.repository.MemberRepository;
import com.app.demo.security.provider.JwtTokenProvider;
import com.app.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberConverter memberConverter;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public Member findUserById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }
    @Override
    public Member signUp(MemberSignupDTO memberSignUpDto) {

        if (memberRepository.findByUserID(memberSignUpDto.getUserID()).isPresent()) {
            throw new UserException(ErrorStatus.USER_EXISTS);
        }

        Member member = Member.builder()
                .userID(memberSignUpDto.getUserID())
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
        return member;
    }

    @Override
    @Transactional
    public LoginResponseDTO.OAuthResponse login(LoginRequestDTO loginRequestDTO) {
        // 사용자 ID로 멤버 조회
        Member member = memberRepository.findByUserID(loginRequestDTO.getUserID())
                .orElseThrow(() -> new AuthException(ErrorStatus.USER_NOT_FOUND)); // 예외 처리 강화 필요

        // 패스워드 검증
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
            throw new AuthException(ErrorStatus.INVALID_PASSWORD);
        }

        // 사용자 인증 정보 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getUserID(), null, List.of(new SimpleGrantedAuthority("USER")));

        // JWT 토큰 생성
        LoginResponseDTO.JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // 리프레시 토큰 업데이트 및 저장
        member.setRefreshToken(jwtToken.getRefreshToken());
        memberRepository.save(member);

        // 토큰 반환
        return memberConverter.convertToOAuthResponse(member, jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }

    // 토큰 새로 고침 로직
    @Transactional
    @Override
    public TokenRefreshResponse refresh(String refreshToken) {
        refreshToken = refreshToken.substring(7);
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(ErrorStatus.AUTH_INVALID_TOKEN);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

        Member member = memberRepository.findByUserID(authentication.getName())
                .orElseThrow(() -> new AuthException(ErrorStatus.USER_NOT_FOUND));

        // 새 토큰 생성
        LoginResponseDTO.JwtToken newTokens = jwtTokenProvider.generateToken(authentication);
        long expiresIn = jwtTokenProvider.getRemainingTime(newTokens.getAccessToken());

        member.setRefreshToken(newTokens.getRefreshToken());
        memberRepository.save(member);

        return new TokenRefreshResponse(newTokens.getAccessToken(), newTokens.getRefreshToken(), "Bearer", expiresIn);
    }


    // 회원 삭제 로직
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
