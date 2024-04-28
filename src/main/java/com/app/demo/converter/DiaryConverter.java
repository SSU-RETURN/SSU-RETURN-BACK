package com.app.demo.converter;

import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.enums.Emotion;

import java.time.LocalDate;

public class DiaryConverter {

    public static DiaryResponseDTO.DiaryContentDTO toDiaryContentDTO(Diary diary){
        return DiaryResponseDTO.DiaryContentDTO.builder()
                .id(diary.getId())
                .member(diary.getMember())
                .AIEmotion(diary.getAiEmotion())
                .memberEmotion(diary.getMemberEmotion())
                .content(diary.getContent())
                .pictureKey(diary.getPictureKey())
                .aiPlaylistId(diary.getAiPlaylist().getId())
                .memberPlaylistId(diary.getMemberPlaylist().getId())
                .writtenDate(diary.getWrittenDate())
                .build();
    }
    public static DiaryResponseDTO.MonthlyDiaryDTO toMonthlyDiaryDTO(Diary diary){
        return DiaryResponseDTO.MonthlyDiaryDTO.builder()
                .id(diary.getId())
                .writtenDate(diary.getWrittenDate())
                .memberEmotion(diary.getMemberEmotion())
                .build();
    }
}
