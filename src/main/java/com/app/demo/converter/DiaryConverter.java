package com.app.demo.converter;

import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.repository.MemberRepository;

import java.time.LocalDate;

public class DiaryConverter {

    public static DiaryResponseDTO.DiaryContentDTO toDiaryContentDTO(Diary diary){
        return DiaryResponseDTO.DiaryContentDTO.builder()
                .id(diary.getDiaryId())
                .member(diary.getMember())
                .memberId(diary.getMemberId())
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
                .id(diary.getDiaryId())
                .writtenDate(diary.getWrittenDate())
                .memberEmotion(diary.getMemberEmotion())
                .build();
    }
}
