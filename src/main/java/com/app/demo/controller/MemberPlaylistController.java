package com.app.demo.controller;


import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.MemberPlaylistInfoConverter;
import com.app.demo.dto.response.MemberPlaylistResponseDTO;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MemberPlaylistMusicRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.MemberPlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.app.demo.converter.MemberPlaylistConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class MemberPlaylistController {

    private final MemberPlaylistService memberPlaylistService;
    private final MemberPlaylistMusicRepository memberPlaylistMusicRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public MemberPlaylistController(MemberPlaylistService memberPlaylistService, MemberPlaylistMusicRepository memberPlaylistMusicRepository, MusicRepository musicRepository){
        this.musicRepository=musicRepository;
        this.memberPlaylistService=memberPlaylistService;
        this.memberPlaylistMusicRepository=memberPlaylistMusicRepository;
    }


    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Member 날짜음악조회", description = "Member플리 날짜로 검색하여 음악 조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/date/{playlistDate}")
    public BaseResponse<MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO> getMemberPlaylistMusicsByDate(@RequestParam(name="memberId") Long memberId, @PathVariable LocalDate playlistDate){
        MemberPlaylist memberPlaylist = memberPlaylistService.getMemberPlaylistByDate(memberId, playlistDate);
        MemberPlaylistConverter memberPlaylistConverter = new MemberPlaylistConverter(musicRepository, memberPlaylistMusicRepository);
        MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO memberPlaylistMusicsDTO = memberPlaylistConverter.toMemberPlaylistMusics(memberPlaylist);
        return BaseResponse.onSuccess(memberPlaylistMusicsDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Member 감정음악조회", description = "Member플리 감정으로 검색하여 음악 조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/emotion/{memberEmotion}")
    public BaseResponse<MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO> getMemberPlaylistMusicsByEmotion(@RequestParam(name="memberId") Long memberId, @PathVariable Emotion memberEmotion){
        List<MemberPlaylist> memberPlaylistList = memberPlaylistService.getMemberPlaylistListByEmotion(memberId, memberEmotion);
        MemberPlaylistConverter memberPlaylistConverter = new MemberPlaylistConverter(musicRepository, memberPlaylistMusicRepository);
        MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO memberPlaylistMusicsDTO = memberPlaylistConverter.toMemberPlaylistListMusics(memberPlaylistList);
        return BaseResponse.onSuccess(memberPlaylistMusicsDTO);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "MemberPlaylistInfo", description = "MemberPlaylist의 간단한 정보를 얻는 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/info/{memberId}")
    public BaseResponse<List<MemberPlaylistResponseDTO.MemberPlaylistInfoDTO>> getMemberPlaylistInfo(@PathVariable Long memberId){
        List<MemberPlaylist> memberPlaylistList = memberPlaylistService.getMemberPlaylistListByMemberId(memberId);
        return BaseResponse.onSuccess(MemberPlaylistInfoConverter.toMemberPlaylistInfo(memberPlaylistList));
    }



    /*
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "test", description = "MemberPlaylist_date의 test API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/test/{memberId}/{playlistDate}")
    public BaseResponse<MemberPlaylistResponseDTO.TestDTO> getDateTest(@PathVariable Long memberId, @PathVariable LocalDate playlistDate){
        MemberPlaylist memberPlaylist = memberPlaylistService.getMemberPlaylistByDate(memberId,playlistDate);
        MemberPlaylistConverter memberPlaylistConverter = new MemberPlaylistConverter(musicRepository, memberPlaylistMusicRepository);
        MemberPlaylistResponseDTO.TestDTO testDTO = memberPlaylistConverter.toTestDateDTO(memberPlaylist);
        return BaseResponse.onSuccess(testDTO);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "test2", description = "MemberPlaylist_emotion의 test API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/test2/{memberId}/{memberEmotion}")
    public BaseResponse<List<MemberPlaylistResponseDTO.TestDTO>> getEmotionTest(@PathVariable Long memberId, @PathVariable Emotion memberEmotion){
        List<MemberPlaylist> memberPlaylist = memberPlaylistService.getMemberPlaylistListByEmotion(memberId,memberEmotion);
        MemberPlaylistConverter memberPlaylistConverter = new MemberPlaylistConverter(musicRepository, memberPlaylistMusicRepository);
        List<MemberPlaylistResponseDTO.TestDTO> testDTOS = memberPlaylistConverter.toTestEmoDTO(memberPlaylist);
        return BaseResponse.onSuccess(testDTOS);
    }
    */



}
