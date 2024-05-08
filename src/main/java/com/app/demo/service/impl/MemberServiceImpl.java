package com.app.demo.service.impl;

import com.app.demo.apiPayload.code.status.ErrorStatus;
import com.app.demo.apiPayload.exception.UserException;
import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.entity.Member;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Member findUserById(Long userId) {
        return memberRepository
                .findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }
    @Override
    public Member signUp(MemberSignupDTO memberSignUpDto) throws Exception {

        if (memberRepository.findByUserID(memberSignUpDto.getUserID()).isPresent()) {
            throw new Exception("이미 존재하는 아이디입니다.");
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

    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
