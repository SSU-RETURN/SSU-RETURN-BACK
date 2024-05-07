package com.app.demo.service;

import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.entity.Member;

public interface MemberService {
    Member findUserById(Long userId);
    public Member signUp(MemberSignupDTO memberSignUpDto) throws Exception;
    void deleteMember(Member member);
}
