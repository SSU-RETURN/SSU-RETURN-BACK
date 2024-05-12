package com.app.demo.dto.response;

import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class AIPlaylistResponseDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AIPlaylistMusicsDTO{
        private List<Music> aiPlaylistMusics;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TestDTO{
        private Long memberId;
        private Long diaryId;
        private Emotion aiEmotion;
        private LocalDate playlistDate;
    }

}
