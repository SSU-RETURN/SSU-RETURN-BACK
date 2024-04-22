package com.app.demo.controller;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;
    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createDiary
            (@RequestBody DiaryRequestDTO.CreateDiaryRequestDTO requestDTO) {
        Diary diary = diaryService.createDiary(requestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Void> updateDiary(@RequestBody DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO) {
        Diary diary = diaryService.updateDiary(requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get-by-month/{memberId}")
    public ResponseEntity<List<DiaryResponseDTO.MonthlyDiaryDTO>> getDiariesByMonth(
            @PathVariable Long memberId,
            @RequestParam LocalDate yearMonth
    ) {
        List<DiaryResponseDTO.MonthlyDiaryDTO> diaries = diaryService.getDiariesByMonth(memberId, yearMonth);
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    @GetMapping("/details/{diaryId}")
    public ResponseEntity<DiaryResponseDTO.DiaryDTO> getDiary(@PathVariable Long diaryId) {
        DiaryResponseDTO.DiaryDTO diary = diaryService.getDiary(diaryId);
        if (diary == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 일기를 찾지 못한 경우
        }
        return new ResponseEntity<>(diary, HttpStatus.OK);
    }

}
