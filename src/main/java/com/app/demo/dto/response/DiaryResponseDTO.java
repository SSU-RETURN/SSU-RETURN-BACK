package com.app.demo.dto.response;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;

public class DiaryResponseDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyDiaryDTO {
        private Long id;
        private LocalDate writtenDate;
        private Emotion memberEmotion;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DiaryContentDTO {
        private Member member;
        private Long memberId;
        private Long id;
        private Emotion memberEmotion;
        private Emotion AIEmotion;
        private LocalDate writtenDate;
        private String content;
        private String pictureKey;
        private Long aiPlaylistId;
        private Long memberPlaylistId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DiaryIdDTO{
        private Long id;
    }

}