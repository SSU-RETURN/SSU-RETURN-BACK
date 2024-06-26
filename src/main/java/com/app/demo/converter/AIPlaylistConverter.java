package com.app.demo.converter;

import com.app.demo.dto.response.AIPlaylistResponseDTO;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.repository.AIPlaylistMusicRepository;
import com.app.demo.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AIPlaylistConverter {
    private final AIPlaylistMusicRepository aiPlaylistMusicRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public AIPlaylistConverter(AIPlaylistMusicRepository aiPlaylistMusicRepository, MusicRepository musicRepository) {
        this.aiPlaylistMusicRepository = aiPlaylistMusicRepository;
        this.musicRepository = musicRepository;
    }

    /*
    public AIPlaylistResponseDTO.AIPlaylistMusicsDTO toAIPlaylistMusics(AIPlaylist aiPlaylist){
        Long aiPlaylistId = aiPlaylist.getAiPlaylistId();
        List<AIPlaylistMusic> aiPlaylistMusicList = aiPlaylistMusicRepository.findByAiPlaylistAiPlaylistId(aiPlaylistId);
        List<Music> aiPlaylistMusics = new ArrayList<>();
        for(AIPlaylistMusic aiPlaylistMusic : aiPlaylistMusicList){
            Long musicId = aiPlaylistMusic.getId();
            musicRepository.findById(musicId).ifPresent(aiPlaylistMusics::add);
        }
        return AIPlaylistResponseDTO.AIPlaylistMusicsDTO.builder()
                .aiPlaylistMusics(aiPlaylistMusics)
                .build();
    }

     */
/*
    public AIPlaylistResponseDTO.TestDTO toAITest(AIPlaylist aiPlaylist){
        return AIPlaylistResponseDTO.TestDTO.builder()
                .playlistDate(aiPlaylist.getPlaylistDate())
                .aiEmotion(aiPlaylist.getAiEmotion())
                .diaryId(aiPlaylist.getDiaryId())
                .memberId(aiPlaylist.getMemberId())
                .build();
    }
    */

}
