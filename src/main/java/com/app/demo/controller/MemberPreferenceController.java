package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.converter.MemberPreferenceConverter;
import com.app.demo.dto.request.MemberPreferenceRequestDTO;
import com.app.demo.dto.response.MemberPreferenceResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.MemberPreference;
import com.app.demo.service.MemberPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/preference")
public class MemberPreferenceController {
    @Autowired
    private final MemberPreferenceService memberPreferenceService;
    public MemberPreferenceController(MemberPreferenceService memberPreferenceService) { this.memberPreferenceService = memberPreferenceService; }

    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "취향저장", description = "취향저장 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON201", description="등록성공")})
    @PostMapping("/create")
    public BaseResponse<MemberPreferenceResponseDTO.MemberPreferenceContentDTO> createMemberPreference(@RequestBody MemberPreferenceRequestDTO.CreateMemberPreferenceRequestDTO requestDTO){
        MemberPreference memberPreference = memberPreferenceService.createMemberPreference(requestDTO);
        MemberPreferenceResponseDTO.MemberPreferenceContentDTO responseDTO = MemberPreferenceConverter.toMemberPreferenceContentDTO(memberPreference);
        return BaseResponse.onSuccess(responseDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "취향업데이트", description = "취향수정 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description="수정성공")})
    @PutMapping("/update")
    public BaseResponse<String> updateMemberPreference(@RequestBody MemberPreferenceRequestDTO.UpdateMemberPreferenceRequestDTO requestDTO){
        MemberPreference memberPreference = memberPreferenceService.updateMemberPreference(requestDTO);
        return BaseResponse.onSuccess("취향수정성공");
    }


    /*
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "취향", description = "취향조회 API입니다")
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회성공")})
    @GetMapping("/details/{memberPreferenceId}")
    public BaseResponse<MemberPreferenceResponseDTO.MemberPreferenceContentDTO> getMemberPreference(@PathVariable Long memberPreferenceId){
        MemberPreference memberPreference = memberPreferenceService.getMemberPreference(memberPreferenceId);
        MemberPreferenceResponseDTO.MemberPreferenceContentDTO responseDTO = MemberPreferenceConverter.toMemberPreferenceContentDTO(memberPreference);
        return BaseResponse.onSuccess(responseDTO);
    }
     */

}
