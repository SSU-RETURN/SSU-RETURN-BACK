package com.app.demo.dto.request;

import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
public class DiaryRequestDTO{
    @Data
    @Builder
    public static class CreateDiaryRequestDTO {
        private Long memberId;
        private String content;
        private Emotion memberEmotion;
        private Emotion aiEmotion;
        private String pictureKey;
        private LocalDate writtenDate;
        //private List<Long> musicList;
    }

    @Data
    @Builder
    public static class UpdateDiaryRequestDTO {
        private Long diaryId;
        private String content;
        private String pictureKey;
    }
}