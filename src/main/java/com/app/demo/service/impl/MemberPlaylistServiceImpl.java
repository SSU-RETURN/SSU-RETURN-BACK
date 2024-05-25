package com.app.demo.service.impl;


import com.app.demo.dto.response.MemberPlaylistResponseDTO;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import com.app.demo.repository.MemberPlaylistMusicRepository;
import com.app.demo.repository.MemberPlaylistRepository;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.MemberPlaylistService;
import com.app.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberPlaylistServiceImpl implements MemberPlaylistService {

    private final MemberPlaylistRepository memberPlaylistRepository;
    private final MemberPlaylistMusicRepository memberPlaylistMusicRepository;
    private final MusicRepository musicRepository;
    private final MusicService musicService;


    @Autowired
    public MemberPlaylistServiceImpl(MemberPlaylistRepository memberPlaylistRepository, MemberPlaylistMusicRepository memberPlaylistMusicRepository, MusicRepository musicRepository, MusicService musicService) {
        this.memberPlaylistRepository = memberPlaylistRepository;
        this.memberPlaylistMusicRepository = memberPlaylistMusicRepository;
        this.musicRepository = musicRepository;
        this.musicService = musicService;
    }

    @Override
    public Page<MemberPlaylistResponseDTO.MusicResponseDTO> getMemberPlaylistByDate(Long memberId, LocalDate playlistDate, int page) {
        MemberPlaylist memberPlaylist = memberPlaylistRepository.findByMemberMemberIdAndPlaylistDate(memberId, playlistDate);
        if (memberPlaylist == null) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, 10);
        Page<MemberPlaylistMusic> memberPlaylistMusicPage = memberPlaylistMusicRepository.findByMemberPlaylistMemberPlaylistId(memberPlaylist.getMemberPlaylistId(), pageable);

        Page<Music> musicPage = memberPlaylistMusicPage.map(mpm -> musicRepository.findById(mpm.getMusic().getId()).orElse(null));

        return memberPlaylistMusicPage.map(mpm -> {
            Music music = musicRepository.findById(mpm.getMusic().getId()).orElse(null);
            if (music == null) return null;
            return new MemberPlaylistResponseDTO.MusicResponseDTO(music.getId(), music.getTitle(), music.getArtist(), music.getPictureKey());
        });
    }



    @Override
    public List<Music> getMemberPlaylistListByEmotion(Long memberId, Emotion memberEmotion, int page) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<MemberPlaylist> memberPlaylistPage = memberPlaylistRepository.findByMemberMemberIdAndMemberEmotion(memberId, memberEmotion, pageable);


        List<Music> musicList = new ArrayList<>();
        for(MemberPlaylist memberPlaylists : memberPlaylistPage.getContent()) {
            List<MemberPlaylistMusic> memberPlaylistMusic = memberPlaylistMusicRepository.findByMemberPlaylistMemberPlaylistId(memberPlaylists.getMemberPlaylistId());
            for (MemberPlaylistMusic musics : memberPlaylistMusic) {
                Optional<Music> music = musicRepository.findById(musics.getMusic().getId());
                music.ifPresent(musicList::add);
            }
        }
        return musicList;
    }

    @Override
    public List<MemberPlaylist> getMemberPlaylistListByMemberId(Long memberId) {
        return memberPlaylistRepository.findByMemberMemberId(memberId);
    }

    @Override
    public MemberPlaylist createMemberPlaylist(Member member, Emotion emotion, LocalDate date) {
        MemberPlaylist playlist = MemberPlaylist.builder()
                .member(member)
                .memberEmotion(emotion)
                .playlistDate(date)
                .build();
        memberPlaylistRepository.save(playlist);
        return playlist;
    }
}

