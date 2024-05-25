package com.app.demo.service.impl;

import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.repository.AIPlaylistMusicRepository;
import com.app.demo.service.AiPlaylistMusicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiPlaylistMusicServiceImpl implements AiPlaylistMusicService {
    private final AIPlaylistMusicRepository aiPlaylistMusicRepository;

    public AiPlaylistMusicServiceImpl(AIPlaylistMusicRepository aiPlaylistMusicRepository) {
        this.aiPlaylistMusicRepository = aiPlaylistMusicRepository;
    }

    @Override
    public void setAiPlaylistMusic(List<Music> musics, AIPlaylist aiPlaylist){
        for (Music musicInfo : musics){
            AIPlaylistMusic aiPlaylistMusic = AIPlaylistMusic.builder()
                    .aiPlaylist(aiPlaylist)
                    .music(musicInfo)
                    .build();
            aiPlaylistMusicRepository.save(aiPlaylistMusic);
        }
    }
}
