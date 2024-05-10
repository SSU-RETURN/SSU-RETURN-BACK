package com.app.demo.service.impl;
import com.app.demo.dto.request.MemberPreferenceRequestDTO;
import com.app.demo.dto.response.MemberPreferenceResponseDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import com.app.demo.repository.MemberPreferenceRepository;
import com.app.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.app.demo.service.MemberPreferenceService;
@Service
public class MemberPreferenceServiceImpl implements MemberPreferenceService{
    private final MemberPreferenceRepository memberPreferenceRepository;
    private final MemberRepository memberRepository;
    public MemberPreferenceServiceImpl(MemberPreferenceRepository memberPreferenceRepository, MemberRepository memberRepository){
        this.memberPreferenceRepository = memberPreferenceRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberPreference createMemberPreference(MemberPreferenceRequestDTO.CreateMemberPreferenceRequestDTO requestDTO){
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        MemberPreference memberPreference = MemberPreference.builder()
                .member(member)
                .genreFirst(requestDTO.getGenreFirst())
                .genreSecond(requestDTO.getGenreSecond())
                .genreThird(requestDTO.getGenreThird())
                .preferenceSad(requestDTO.getPreferenceSad())
                .preferenceHappy(requestDTO.getPreferenceHappy())
                .preferenceAngry(requestDTO.getPreferenceAngry())
                .preferenceRomance(requestDTO.getPreferenceRomance())
                .preferenceAnxious(requestDTO.getPreferenceAnxious())
                .build();
        return memberPreferenceRepository.save(memberPreference);
    }

    @Override
    public MemberPreference updateMemberPreference(MemberPreferenceRequestDTO.UpdateMemberPreferenceRequestDTO requestDTO){
        MemberPreference memberPreference = MemberPreference.builder()
                .memberPreferenceId(requestDTO.getMemberPreferenceId())
                .genreFirst(requestDTO.getGenreFirst())
                .genreSecond(requestDTO.getGenreSecond())
                .genreThird(requestDTO.getGenreThird())
                .preferenceSad(requestDTO.getPreferenceSad())
                .preferenceHappy(requestDTO.getPreferenceHappy())
                .preferenceAngry(requestDTO.getPreferenceAngry())
                .preferenceRomance(requestDTO.getPreferenceRomance())
                .preferenceAnxious(requestDTO.getPreferenceAnxious())
                .build();
        return memberPreferenceRepository.save(memberPreference);
    }

    @Override
    public MemberPreference getMemberPreference(Long memberPreferenceId) {
        return memberPreferenceRepository.findById(memberPreferenceId).orElse(null);
    }
}