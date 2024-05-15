package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.DiaryConverter;
import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    @Autowired
    private final DiaryService diaryService;
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }


    /*
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
     */

    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "일기생성", description = "일기생성 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON201", description="등록성공")})
    @PostMapping("/create")
    public BaseResponse<DiaryResponseDTO.DiaryContentDTO> createDiary(@RequestBody DiaryRequestDTO.CreateDiaryRequestDTO requestDTO) {
        Diary diary = diaryService.createDiary(requestDTO);
        DiaryResponseDTO.DiaryContentDTO responseDTO = DiaryConverter.toDiaryContentDTO(diary);
        return BaseResponse.onSuccess(responseDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "일기수정", description = "일기수정 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "수정성공")})
    @PutMapping("/update")
    public BaseResponse<String> updateDiary(@RequestBody DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO){
        Diary diary =diaryService.updateDiary(requestDTO);
        return BaseResponse.onSuccess("일기수정완료");
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "일기삭제", description = "일기삭제 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "삭제성공")})
    @DeleteMapping("/delete/{diaryId}")
    public BaseResponse<String> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return BaseResponse.onSuccess("일기삭제완료");
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "월별일기조회", description = "월별일기조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/monthly/{memberId}")
    public BaseResponse<List<DiaryResponseDTO.MonthlyDiaryDTO>> getDiariesByMonth(@PathVariable Long memberId, @RequestParam(name = "YearMonth") LocalDate yearMonth){

        List<Diary> diaries = diaryService.getDiariesByMonth(memberId, yearMonth);
        List<DiaryResponseDTO.MonthlyDiaryDTO> responseDTOList = diaries.stream()
                .map(DiaryConverter::toMonthlyDiaryDTO)
                .collect(Collectors.toList());
        return BaseResponse.onSuccess(responseDTOList);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "일기조회", description = "일기조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/details/{diaryId}")
    public BaseResponse<DiaryResponseDTO.DiaryContentDTO> getDiary(@PathVariable Long diaryId){
        Diary diary = diaryService.getDiary(diaryId);
        DiaryResponseDTO.DiaryContentDTO responseDTO = DiaryConverter.toDiaryContentDTO(diary);
        return BaseResponse.onSuccess(responseDTO);
    }
}
