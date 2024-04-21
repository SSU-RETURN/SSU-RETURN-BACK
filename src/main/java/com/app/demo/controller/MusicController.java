package com.app.demo.controller;


import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

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
    @Operation(summary = "accessToken발급", description = "토큰 받아오는 API입니다.")
    @GetMapping("/getToken")
    private String getSpotifyToken(){
        return musicService.getAccessToken();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "음악검색", description = "음악검색 API입니다.")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회 성공")})
    @GetMapping("/search")
    public List<MusicResponseDTO.SearchResponseDTO> search(@RequestParam("keyword") String keyword,
                                                           @RequestParam(name = "page", defaultValue = "0") int page) {
        return musicService.searchMusic(keyword, page);
    }
}
