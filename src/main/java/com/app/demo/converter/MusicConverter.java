package com.app.demo.converter;

import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;

import java.util.List;
import java.util.stream.Collectors;

public class MusicConverter {
    public static MusicResponseDTO.MusicContentDTO toMusicContentDTO(Music music) {
        return MusicResponseDTO.MusicContentDTO.builder()
                .id(music.getId())
                .artist(music.getArtist())
                .title(music.getTitle())
                .pictureKey(music.getPictureKey())
                .build();
    }

    public static MusicRequestDTO.MusicContentDTO searchContentDTOToMusicContentDTO(MusicResponseDTO.MusicSearchContentDTO searchContentDTO) {
        return new MusicRequestDTO.MusicContentDTO(
                searchContentDTO.getArtist(),
                searchContentDTO.getTitle(),
                searchContentDTO.getPictureKey()
                );
    }

    public static MusicRequestDTO.SaveMusicDTO toSaveMusicDTO(List<MusicRequestDTO.MusicContentDTO> searchDTOs) {
        return MusicRequestDTO.SaveMusicDTO.builder()
                .musics(searchDTOs)
                .build();
    }
}
