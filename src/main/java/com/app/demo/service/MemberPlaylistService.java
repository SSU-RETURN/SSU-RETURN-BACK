package com.app.demo.service;

import com.app.demo.dto.response.MemberPlaylistResponseDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.Music;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface MemberPlaylistService {
    public Page<MemberPlaylistResponseDTO.MusicResponseDTO> getMemberPlaylistByDate(Long memberId, LocalDate playlistDate, int page);
    List<Music> getMemberPlaylistListByEmotion(Long memberId, Emotion memberEmotion, int page);
    List<MemberPlaylist> getMemberPlaylistListByMemberId(Long memberId);
    MemberPlaylist createMemberPlaylist(Member member, Emotion emotion, LocalDate date);
}
