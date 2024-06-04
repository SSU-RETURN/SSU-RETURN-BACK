package com.app.demo.controller;


import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.RecommendPageConverter;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Member;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.AIPlaylistService;
import com.app.demo.service.MemberPlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.app.demo.entity.Diary;
import com.app.demo.dto.response.RecommendPageResponseDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("api/recommend")
public class RecommendPageController {

    @Autowired
    private final DiaryRepository diaryRepository;
    private final MemberPlaylistService memberPlaylistservice;
    private final AIPlaylistService aiPlaylistService;
    private final MemberRepository memberRepository;

    public RecommendPageController(DiaryRepository diaryRepository, MemberPlaylistService memberPlaylistservice, AIPlaylistService aiPlaylistService, MemberRepository memberRepository) {
        this.diaryRepository = diaryRepository;
        this.memberPlaylistservice = memberPlaylistservice;
        this.aiPlaylistService = aiPlaylistService;
        this.memberRepository = memberRepository;
    }


    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "추천음악 페이지", description = "추천음악 페이지 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description="추천음악 반환")})
    @GetMapping("/{memberId}/{date}")
    public BaseResponse<RecommendPageResponseDTO.RecommendPageDTO> getRecommendPage (@PathVariable Long memberId, @PathVariable LocalDate date){
        Member member = memberRepository.findByMemberId(memberId);
        Diary diary = diaryRepository.findByMemberAndWrittenDate(member,date);
        if (diary == null) {
            RecommendPageResponseDTO.RecommendPageDTO responseDTO = RecommendPageConverter.toNullDTO();
            return BaseResponse.onFailure("COMMON404", "일기가없습니다", null);
        }else{
            Emotion emotion = diary.getMemberEmotion();
            List<Music> memberEmotionPlaylist = memberPlaylistservice.getMemberPlaylistListByEmotion(memberId,emotion,0);
            List<Music> aiPlaylist = aiPlaylistService.getAiPlaylist(diary.getDiaryId());
            Long diaryId = diary.getDiaryId();
            RecommendPageResponseDTO.RecommendPageDTO responseDTO = RecommendPageConverter.toRecommendPageDTO(aiPlaylist, memberEmotionPlaylist, diaryId);
            return BaseResponse.onSuccess(responseDTO);
        }
    }

}