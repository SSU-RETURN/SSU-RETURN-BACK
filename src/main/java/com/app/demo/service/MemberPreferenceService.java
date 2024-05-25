package com.app.demo.service;


import com.app.demo.dto.request.MemberPreferenceRequestDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import com.app.demo.entity.enums.Preference;

public interface MemberPreferenceService {

    MemberPreference createMemberPreference(MemberPreferenceRequestDTO.CreateMemberPreferenceRequestDTO requestDTO);
    MemberPreference updateMemberPreference(MemberPreferenceRequestDTO.UpdateMemberPreferenceRequestDTO requestDTO);
    MemberPreference getMemberPreferenceByMemberId(Long memberId);
    void deleteMemberPreferenceByMemberId(Long memberId);
    Preference getMemberPreferenceForGPT(Member member, String emotion);
}
