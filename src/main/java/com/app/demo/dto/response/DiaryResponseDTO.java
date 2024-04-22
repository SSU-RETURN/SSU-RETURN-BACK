package com.app.demo.dto.response;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.entity.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class DiaryResponseDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyDiaryDTO {
        private Long id;
        private LocalDate writtenDate;
        private Emotion memberEmotion;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiaryDTO {
        private String content;
        private String pictureKey;
        private Long aiPlaylistId;
        private Long memberPlaylistId;
    }

}