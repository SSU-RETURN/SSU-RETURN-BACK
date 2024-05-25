package com.app.demo.dto.response;

import com.app.demo.entity.Diary;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemberPlaylistResponseDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberPlaylistMusicsDTO{
        private List<Music> memberPlaylistMusics;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberPlaylistInfoDTO{
        private LocalDate playlistDate;
        private String pictureKey;
        private Emotion memberEmotion;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TestDTO{
        private Long playlistId;
        private String pictureKey;
        private Long memberId;
        private Long diaryId;
        private Diary diary;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MusicResponseDTO{
        private Long id;
        private String title;
        private String artist;
        private String pictureKey;
    }
}
