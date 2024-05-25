package com.app.demo.service;

import com.app.demo.dto.request.MemberPreferencePlaylistRequestDTO;
import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;

import java.util.List;

public interface MemberPreferencePlaylistService {
    MemberPreferencePlaylist createMemberPreferencePlaylist(Long memberId);
    Boolean updateMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.UpdateMemberPreferencePlaylistDTO updateMemberPreferencePlaylistDTO);
    List<Music> getMemberPreferencePlaylistByMember(Long memberId);


}
