package com.app.demo.controller;


import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.MemberPreferencePlaylistConverter;
import com.app.demo.converter.MemberPreferencePlaylistMusicsConverter;
import com.app.demo.dto.request.MemberPreferencePlaylistRequestDTO;
import com.app.demo.dto.response.MemberPreferencePlaylistResponseDTO;
import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;
import com.app.demo.repository.MemberPreferencePlaylistMusicRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.MemberPreferencePlaylistService;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class MemberPreferencePlaylistController {


    @Autowired
    private final MemberPreferencePlaylistService memberPreferencePlaylistService;
    private final MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository;


    public MemberPreferencePlaylistController(MemberPreferencePlaylistService memberPreferencePlaylistService, MemberRepository memberRepository, MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository) {
        this.memberPreferencePlaylistService = memberPreferencePlaylistService;
        this.memberPreferencePlaylistMusicRepository = memberPreferencePlaylistMusicRepository;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "취향플리생성", description = "취향플리생성 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON201", description="등록성공")})
    @PostMapping("/preference/create")
    public BaseResponse<MemberPreferencePlaylistResponseDTO.CreateMemberPreferencePlaylistDTO> createMemberPreferencePlaylist(@RequestParam Long memberId){

        if(!memberPreferencePlaylistService.getMemberPreferencePlaylistByMember(memberId).isEmpty()){
            return BaseResponse.onFailure("COMMON400", "이미 취향플레이리스트가 존재합니다", null);
        }else {
            MemberPreferencePlaylist memberPreferencePlaylist = memberPreferencePlaylistService.createMemberPreferencePlaylist(memberId);
            MemberPreferencePlaylistResponseDTO.CreateMemberPreferencePlaylistDTO responseDTO = MemberPreferencePlaylistConverter.toCreateDTO(memberPreferencePlaylist);
            return BaseResponse.onSuccess(responseDTO);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "취향플리갱신", description = "취향플리갱신 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "갱신성공"), @ApiResponse(responseCode = "304", description = "Not Modified")})
    @PutMapping("/preference/update")
    public ResponseEntity<BaseResponse<String>> updateMemberPreferencePlaylist(@RequestBody MemberPreferencePlaylistRequestDTO.UpdateMemberPreferencePlaylistDTO requestDTO) {
        Boolean isUpdated = memberPreferencePlaylistService.updateMemberPreferencePlaylist(requestDTO);
        if (isUpdated) {
            return ResponseEntity.ok(BaseResponse.onSuccess("갱신 성공"));
        } else {
            return ResponseEntity.ok(BaseResponse.onFailure("200", "데이터가 최신 상태입니다", null));
        }

    }
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "취향플리검색", description = "취향플리검색 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description="검색성공")})
    @GetMapping("/preference/musics/{memberId}")
    public BaseResponse<List<Music>> getMemberPreferencePlaylistMusics(@PathVariable Long memberId){
        List<Music> musics  = memberPreferencePlaylistService.getMemberPreferencePlaylistByMember(memberId);
        return BaseResponse.onSuccess(musics);
    }

}
