package com.app.demo.service;

import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;

import java.util.List;

public interface MusicService {
    Music getMusicFromApi();
    String getAccessToken();


    List<MusicResponseDTO.SearchResponseDTO> searchMusic(String keyword, int page);
}
