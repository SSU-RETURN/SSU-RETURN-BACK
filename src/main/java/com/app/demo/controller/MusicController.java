package com.app.demo.controller;


import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.MusicConverter;
import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;
import com.app.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/music")
@RequiredArgsConstructor
public class MusicController {

    @Autowired
    private MusicService musicService;
    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }


    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회 성공")})
    @Operation(summary = "SpotifyAccessToken발급", description = "Spotify 토큰 받아오는 API입니다.")
    @GetMapping("/getToken")
    private BaseResponse<String> getSpotifyToken(){
        String token = musicService.getAccessToken();
        return BaseResponse.onSuccess(token);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "음악검색", description = "음악검색 API입니다.")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회 성공")})
    @GetMapping("/search")
    public BaseResponse<List<MusicResponseDTO.MusicSearchContentDTO>> search(@RequestParam("keyword") String keyword,
                                                           @RequestParam(name = "page", defaultValue = "0") int page) {
        List<MusicResponseDTO.MusicSearchContentDTO> musics = musicService.searchMusic(keyword, page);
        return BaseResponse.onSuccess(musics);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "음악저장", description = "음악저장 API입니다.")
    @ApiResponses({@ApiResponse(responseCode = "COMMON201", description = "등록 성공")})
    @PostMapping("/save")
    public BaseResponse<List<MusicResponseDTO.MusicContentDTO>> save(@RequestBody MusicRequestDTO.SaveMusicDTO request) {
        List<Music> musics = musicService.saveMusic(request);

        List<MusicResponseDTO.MusicContentDTO> responseDTOList = musics.stream()
                .map(MusicConverter::toMusicContentDTO)
                .collect(Collectors.toList());
        return BaseResponse.onSuccess(responseDTOList);
    }


}
