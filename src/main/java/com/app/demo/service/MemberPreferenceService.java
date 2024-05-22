package com.app.demo.service;


import com.app.demo.dto.request.MemberPreferenceRequestDTO;
import com.app.demo.entity.MemberPreference;

public interface MemberPreferenceService {

    MemberPreference createMemberPreference(MemberPreferenceRequestDTO.CreateMemberPreferenceRequestDTO requestDTO);
    MemberPreference updateMemberPreference(MemberPreferenceRequestDTO.UpdateMemberPreferenceRequestDTO requestDTO);
    MemberPreference getMemberPreferenceByMemberId(Long memberId);
}
