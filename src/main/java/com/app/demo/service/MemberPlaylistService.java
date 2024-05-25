package com.app.demo.service;

import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.Music;

import java.time.LocalDate;
import java.util.List;

public interface MemberPlaylistService {
    MemberPlaylist getMemberPlaylistByDate(Long memberId, LocalDate playlistDate);
    List<MemberPlaylist> getMemberPlaylistListByEmotion(Long memberId, Emotion memberEmotion);
    List<MemberPlaylist> getMemberPlaylistListByMemberId(Long memberId);
    MemberPlaylist createMemberPlaylist(Member member);
}
