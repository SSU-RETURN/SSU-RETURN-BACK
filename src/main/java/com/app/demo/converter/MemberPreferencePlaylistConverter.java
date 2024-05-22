package com.app.demo.converter;

import com.app.demo.dto.response.MemberPreferencePlaylistResponseDTO;
import com.app.demo.entity.MemberPreferencePlaylist;

public class MemberPreferencePlaylistConverter {

    public static MemberPreferencePlaylistResponseDTO.CreateMemberPreferencePlaylistDTO toCreateDTO(MemberPreferencePlaylist memberPreferencePlaylist){
        return MemberPreferencePlaylistResponseDTO.CreateMemberPreferencePlaylistDTO.builder()
                .memberPreferencePlaylistId(memberPreferencePlaylist.getId())
                .build();
    }
}
