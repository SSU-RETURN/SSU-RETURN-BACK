package com.app.demo.controller;
import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.AIPlaylistConverter;
import com.app.demo.dto.response.AIPlaylistResponseDTO;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.repository.AIPlaylistMusicRepository;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.AIPlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/AIPlaylist")
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
    @GetMapping("/details/{aiPlaylistID}")
    public BaseResponse<AIPlaylistResponseDTO.AIPlaylistMusicsDTO> getAIPlaylistMusics(@PathVariable Long aiPlaylistId){
        AIPlaylist aiPlaylist = aiPlaylistService.getAIPlaylist(aiPlaylistId);
        AIPlaylistConverter converter = new AIPlaylistConverter(aiPlaylistMusicRepository, musicRepository);
        AIPlaylistResponseDTO.AIPlaylistMusicsDTO responseDTO = converter.toAIPlaylistMusics(aiPlaylist);
        return BaseResponse.onSuccess(responseDTO);
    }


}
