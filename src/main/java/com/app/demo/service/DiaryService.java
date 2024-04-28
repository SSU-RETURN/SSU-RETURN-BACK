package com.app.demo.service;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO);
    Diary updateDiary(DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO);
    void deleteDiary(Long diaryId);
    List<Diary> getDiariesByMonth(Member member, LocalDate yearMonth);

    Diary getDiary(Long diaryId);

}