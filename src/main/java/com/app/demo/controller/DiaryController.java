package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.DiaryConverter;
import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.AiEmotion;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.service.AIPlaylistService;
import com.app.demo.service.AiEmotionService;
import com.app.demo.service.DiaryService;
import com.app.demo.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    @Autowired
    private final DiaryService diaryService;

    @Autowired
    private final AiEmotionService aiEmotionService;
    private final AIPlaylistService aiPlaylistService;
    private final S3ImageService s3ImageService;
    public DiaryController(DiaryService diaryService, AiEmotionService aiEmotionService, AIPlaylistService aiPlaylistService, S3ImageService s3ImageService) {
        this.diaryService = diaryService;
        this.aiEmotionService = aiEmotionService;
        this.aiPlaylistService = aiPlaylistService;
        this.s3ImageService = s3ImageService;
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
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<DiaryResponseDTO.DiaryIdDTO> createDiary(@RequestPart("diary") DiaryRequestDTO.CreateDiaryRequestDTO requestDTO,
                                                                 @RequestPart(value = "image", required = false) MultipartFile image) {
        String imageUrl = (image != null) ? s3ImageService.upload(image) : null;
        Diary diary = diaryService.createDiary(requestDTO, imageUrl);
        DiaryResponseDTO.DiaryIdDTO responseDTO = DiaryConverter.toDiaryIdDTO(diary);
        return BaseResponse.onSuccess(responseDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "일기수정", description = "일기수정 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "수정성공")})
    @PutMapping("/update")
    public BaseResponse<String> updateDiary(@RequestBody DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO){
        diaryService.updateDiary(requestDTO);
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

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "일기 감정 분석 결과 조회", description = "일기감정 분석결과 조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/emotion/{diaryId}")
    public BaseResponse<DiaryResponseDTO.EmotionDTO> getEmotionFromDiary(@PathVariable Long diaryId){
        AiEmotion aiEmotion = diaryService.getAiEmotionFromDiary(diaryId);
        DiaryResponseDTO.EmotionDTO responseDTO = DiaryConverter.diaryAiEmotion(aiEmotion);
        return BaseResponse.onSuccess(responseDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "오늘의 일기의 감정 조회", description = "오늘의 일기의 감정을 조회합니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/{memberId}/{date}")
    public BaseResponse<DiaryResponseDTO.todayEmotionDTO> getTodayEmotion(@PathVariable Long memberId, @PathVariable LocalDate date){
        Diary diary = diaryService.getDiaryByMemberDate(memberId, date);
        if(diary == null){
            return BaseResponse.onFailure("COMMON404", "오늘의 일기가 없습니다", null);
        }
        DiaryResponseDTO.todayEmotionDTO responseDTO = DiaryConverter.toTodayEmotion(diary);
        return BaseResponse.onSuccess(responseDTO);
    }
}
