package com.app.demo.converter;

import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.AiEmotion;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.repository.MemberRepository;

import java.time.LocalDate;

public class DiaryConverter {

    public static DiaryResponseDTO.DiaryContentDTO toDiaryContentDTO(Diary diary){
        return DiaryResponseDTO.DiaryContentDTO.builder()
                .id(diary.getDiaryId())
               // .AIEmotion(diary.getAiEmotion())
                .memberEmotion(diary.getMemberEmotion())
                .content(diary.getContent())
                .pictureKey(diary.getPictureKey())
                .aiPlaylistId(diary.getAiPlaylist().getAiPlaylistId())
                .memberPlaylistId(diary.getMemberPlaylist().getMemberPlaylistId())
                .writtenDate(diary.getWrittenDate())
                .build();
    }
    public static DiaryResponseDTO.MonthlyDiaryDTO toMonthlyDiaryDTO(Diary diary){
        return DiaryResponseDTO.MonthlyDiaryDTO.builder()
                .id(diary.getDiaryId())
                .writtenDate(diary.getWrittenDate())
                .memberEmotion(diary.getMemberEmotion())
                .build();
    }

    public static DiaryResponseDTO.DiaryIdDTO toDiaryIdDTO(Diary diary){
        return DiaryResponseDTO.DiaryIdDTO.builder()
                .id(diary.getDiaryId())
                .build();
    }

    public static DiaryResponseDTO.EmotionDTO diaryAiEmotion(AiEmotion aiEmotion){
        return DiaryResponseDTO.EmotionDTO.builder()
                .sad(aiEmotion.getSad())
                .happy(aiEmotion.getHappy())
                .angry(aiEmotion.getAngry())
                .surprise(aiEmotion.getSurprise())
                .build();
    }
    public static DiaryResponseDTO.todayEmotionDTO toTodayEmotion(Diary diary){
        return DiaryResponseDTO.todayEmotionDTO.builder()
                .emotion(diary.getMemberEmotion())
                .build();
    }
}
