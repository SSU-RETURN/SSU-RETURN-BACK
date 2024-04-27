package com.app.demo.converter;

import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;

import java.util.List;

public class MusicConverter {
    public static MusicResponseDTO.MusicContentDTO toMusicContentDTO(Music music) {
        return MusicResponseDTO.MusicContentDTO.builder()
                .id(music.getId())
                .artist(music.getArtist())
                .title(music.getTitle())
                .pictureKey(music.getPictureKey())
                .build();
    }
}
