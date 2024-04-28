package com.app.demo.service;

import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.entity.Music;

import java.util.List;

public interface MusicService {

    String getAccessToken();

    List<MusicResponseDTO.MusicSearchContentDTO> searchMusic(String keyword, int page);

    List<Music> saveMusic(MusicRequestDTO.SaveMusicDTO request);
}
