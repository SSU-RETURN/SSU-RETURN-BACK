package com.app.demo.dto.response;

import com.app.demo.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AIPlaylistResponseDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AIPlaylistMusicsDTO{
        private List<Music> AIPlaylistMusics;
    }

}
