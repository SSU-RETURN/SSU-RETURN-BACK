package com.app.demo.controller;
import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.AIPlaylistConverter;
import com.app.demo.dto.response.AIPlaylistResponseDTO;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Music;
import com.app.demo.repository.AIPlaylistMusicRepository;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.AIPlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class AIPlaylistController {

    private final AIPlaylistService aiPlaylistService;
    private final AIPlaylistMusicRepository aiPlaylistMusicRepository;
    private final MusicRepository musicRepository;



    @Autowired
    public AIPlaylistController(AIPlaylistService aiPlaylistService,
                                AIPlaylistMusicRepository aiPlaylistMusicRepository,
                                MusicRepository musicRepository) {
        this.aiPlaylistService = aiPlaylistService;
        this.aiPlaylistMusicRepository = aiPlaylistMusicRepository;
        this.musicRepository = musicRepository;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "AI플레이리스트음악조회", description = "AI플레이리스트 내 음악 조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/ai/{diaryId}")
    public BaseResponse<List<Music>> getAIPlaylistMusics(@PathVariable Long diaryId){
        List<Music> aiPlaylist = aiPlaylistService.getAiPlaylist(diaryId);
        return BaseResponse.onSuccess(aiPlaylist);
    }



    /*
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "AI플레이리스트 테스트조회", description = "AI플레이리스트 테스트 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/test/{diaryId}")
    public BaseResponse<AIPlaylistResponseDTO.TestDTO> getAiTest(@PathVariable Long diaryId){
        AIPlaylist aiPlaylist = diaryRepository.findByDiaryId(diaryId).getAiPlaylist();
        AIPlaylistConverter converter = new AIPlaylistConverter(aiPlaylistMusicRepository, musicRepository);
        AIPlaylistResponseDTO.TestDTO responseDTO = converter.toAITest(aiPlaylist);
        return BaseResponse.onSuccess(responseDTO);
    }
    */


}
