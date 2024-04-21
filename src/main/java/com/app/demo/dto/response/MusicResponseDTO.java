package com.app.demo.dto.response;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MusicResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResponseDTO {
        private String artistName;
        private String title;
        private String imageUrl;
    }
}
