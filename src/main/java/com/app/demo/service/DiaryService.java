package com.app.demo.service;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO);
    Diary updateDiary(DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO);
    void deleteDiary(Long diaryId);
    List<DiaryResponseDTO.MonthlyDiaryDTO> getDiariesByMonth(Long memberId, LocalDate yearMonth);

    DiaryResponseDTO.DiaryDTO getDiary(Long diaryId);

}