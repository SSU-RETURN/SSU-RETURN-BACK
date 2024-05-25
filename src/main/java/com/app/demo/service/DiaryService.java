package com.app.demo.service;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.entity.AiEmotion;
import com.app.demo.entity.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO, String imageUrl);
    void updateDiary(DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO);
    void deleteDiary(Long diaryId);
    List<Diary> getDiariesByMonth(Long memberId, LocalDate yearMonth);
    Diary getDiary(Long diaryId);
    AiEmotion getAiEmotionFromDiary(Long diaryId);
}