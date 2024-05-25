package com.app.demo.service.impl;
import com.app.demo.dto.request.MemberPreferenceRequestDTO;
import com.app.demo.dto.response.MemberPreferenceResponseDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import com.app.demo.entity.enums.Preference;
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
                .preferenceSurprise(requestDTO.getPreferenceSurprise())
                .build();
        return memberPreferenceRepository.save(memberPreference);
    }

    @Override
    public MemberPreference updateMemberPreference(MemberPreferenceRequestDTO.UpdateMemberPreferenceRequestDTO requestDTO){
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        Long memberPreferenceId = memberPreferenceRepository.findByMember(member).getMemberPreferenceId();
        MemberPreference memberPreference = MemberPreference.builder()
                .memberPreferenceId(memberPreferenceId)
                .member(member)
                .genreFirst(requestDTO.getGenreFirst())
                .genreSecond(requestDTO.getGenreSecond())
                .genreThird(requestDTO.getGenreThird())
                .preferenceSurprise(requestDTO.getPreferenceSurprise())
                .preferenceRomance(requestDTO.getPreferenceRomance())
                .preferenceAngry(requestDTO.getPreferenceAngry())
                .preferenceHappy(requestDTO.getPreferenceHappy())
                .preferenceSad(requestDTO.getPreferenceSad())
                .build();

        return memberPreferenceRepository.save(memberPreference);
    }

    @Override
    public MemberPreference getMemberPreferenceByMemberId(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        return memberPreferenceRepository.findByMember(member);
    }

    @Override
    public void deleteMemberPreferenceByMemberId(Long memberId){
        Member member = memberRepository.findByMemberId(memberId);
        memberPreferenceRepository.deleteByMember(member);
        return;
    }

    @Override
    public Preference getMemberPreferenceForGPT(Member member, String emotion){
        MemberPreference memberPreference = memberPreferenceRepository.findByMember(member);
        return switch (emotion) {
            case "HAPPY" -> memberPreference.getPreferenceHappy();
            case "SAD" -> memberPreference.getPreferenceSad();
            case "ANGRY" -> memberPreference.getPreferenceAngry();
            case "ROMANCE" -> memberPreference.getPreferenceRomance();
            default -> memberPreference.getPreferenceSurprise();
        };
    }


}
