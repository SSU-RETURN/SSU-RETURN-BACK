package com.app.demo.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MusicResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicContentDTO {
        private Long id;
        private String artist;
        private String title;
        private String pictureKey;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicSearchContentDTO {
        private String artist;
        private String title;
        private String pictureKey;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicSearchDTO{
        private List<MusicSearchContentDTO> musicSearchData;
    }
}
