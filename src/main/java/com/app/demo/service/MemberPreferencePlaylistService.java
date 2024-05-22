package com.app.demo.service;

import com.app.demo.dto.request.MemberPreferencePlaylistRequestDTO;
import com.app.demo.entity.MemberPreferencePlaylist;

public interface MemberPreferencePlaylistService {
    MemberPreferencePlaylist createMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.CreateMemberPreferencePlaylistDTO memberPreferencePlaylistRequestDTO);
    Boolean updateMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.UpdateMemberPreferencePlaylistDTO updateMemberPreferencePlaylistDTO);
    MemberPreferencePlaylist getMemberPreferencePlaylistByMember(Long memberId);
}
