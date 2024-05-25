package com.app.demo.service.impl;


import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import com.app.demo.repository.MemberPlaylistMusicRepository;
import com.app.demo.repository.MemberPlaylistRepository;
import com.app.demo.service.MemberPlaylistService;
import com.app.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberPlaylistServiceImpl implements MemberPlaylistService {

    private final MemberPlaylistRepository memberPlaylistRepository;
    private MusicService musicService;


    @Autowired
    public MemberPlaylistServiceImpl(MemberPlaylistRepository memberPlaylistRepository) {
        this.memberPlaylistRepository = memberPlaylistRepository;
    }

    @Override
    public MemberPlaylist getMemberPlaylistByDate(Long memberId, LocalDate playlistDate) {
        return memberPlaylistRepository.findByMemberIdAndPlaylistDate(memberId, playlistDate);
    }

    @Override
    public List<MemberPlaylist> getMemberPlaylistListByEmotion(Long memberId, Emotion memberEmotion) {
        return memberPlaylistRepository.findByMemberIdAndMemberEmotion(memberId, memberEmotion);
    }

    @Override
    public List<MemberPlaylist> getMemberPlaylistListByMemberId(Long memberId) {
        return memberPlaylistRepository.findByMemberId(memberId);
    }

    @Override
    public MemberPlaylist createMemberPlaylist(Member member, Emotion emotion) {
        MemberPlaylist playlist = MemberPlaylist.builder()
                .member(member)
                .memberEmotion(emotion)
                .build();
        memberPlaylistRepository.save(playlist);
        return playlist;
    }
}

