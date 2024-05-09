package com.app.demo.security.principal;

import com.app.demo.apiPayload.code.status.ErrorStatus;
import com.app.demo.apiPayload.exception.AuthException;
import com.app.demo.entity.Member;
import com.app.demo.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userMemberId) throws UsernameNotFoundException {
        Member member =
                memberRepository
                        .findById(Long.parseLong(userMemberId))
                        .orElseThrow(() -> new AuthException(ErrorStatus.USER_NOT_FOUND));

        return new PrincipalDetails(member);
    }
}