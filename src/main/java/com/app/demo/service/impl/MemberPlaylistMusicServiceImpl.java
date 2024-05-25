package com.app.demo.service.impl;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import com.app.demo.repository.MemberPlaylistMusicRepository;
import com.app.demo.service.MemberPlaylistMusicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberPlaylistMusicServiceImpl implements MemberPlaylistMusicService {

    private final MemberPlaylistMusicRepository memberPlaylistMusicRepository;

    public MemberPlaylistMusicServiceImpl(MemberPlaylistMusicRepository memberPlaylistMusicRepository) {
        this.memberPlaylistMusicRepository = memberPlaylistMusicRepository;
    }

    @Override
    public void setMemberPlaylistMusic(List<Music> musics, MemberPlaylist memberPlaylist){
        for (Music musicInfo : musics){
            MemberPlaylistMusic memberPlaylistMusic = MemberPlaylistMusic.builder()

                    .memberPlaylist(memberPlaylist)
                    .music(musicInfo)
                    .build();
            memberPlaylistMusicRepository.save(memberPlaylistMusic);
        }
    }
}
