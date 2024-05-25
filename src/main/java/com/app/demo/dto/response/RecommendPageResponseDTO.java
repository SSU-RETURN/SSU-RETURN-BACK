package com.app.demo.dto.response;


import com.app.demo.entity.Music;
import lombok.*;

import java.util.List;

public class RecommendPageResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecommendPageDTO{
        private List<Music> aiPlaylist;
        private List<Music> memberEmotionPlaylist;
    }
}
