package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.dto.request.LoginRequestDTO;
import com.app.demo.dto.request.MemberRequestDTO;
import com.app.demo.dto.response.LoginResponseDTO;
import com.app.demo.dto.response.TokenRefreshResponse;
import com.app.demo.entity.Member;
import com.app.demo.security.handler.annotation.ExtractToken;
import com.app.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "회원가입", description = "회원가입 API입니다.")
    @ApiResponses({@ApiResponse(responseCode = "COMMON201", description = "등록 성공")})
    @PostMapping("/signup")
    public BaseResponse<String> signUp(@RequestBody MemberSignupDTO memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return BaseResponse.onSuccess("회원가입 성공");
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회 성공")})
    @Operation(summary = "로그인", description = "로그인 API입니다.")
    @PostMapping("/login")
    private BaseResponse<LoginResponseDTO.OAuthResponse> login(@RequestBody LoginRequestDTO loginRequest){
        LoginResponseDTO.OAuthResponse response = memberService.login(loginRequest);
        return BaseResponse.onSuccess(response);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "조회 성공")})
    @Operation(summary = "JWT Access Token 재발급 API", description = "Refresh Token을 검증하고 새로운 Access Token과 Refresh Token을 응답합니다.")
    @PostMapping("/refresh")
    public BaseResponse<TokenRefreshResponse> refresh(@ExtractToken String refreshToken) {
        TokenRefreshResponse response = memberService.refresh(refreshToken);
        return BaseResponse.onSuccess(response);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "삭제 성공")})
    @Operation(summary = "회원탈퇴 API", description = "회원을 삭제합니다.")
    @DeleteMapping("/delete")
    public BaseResponse<String> delete(@RequestParam Long memberId) {
        memberService.deleteMember(memberId);
        return BaseResponse.onSuccess("회원탈퇴 성공");
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "수정 성공")})
    @Operation(summary = "닉네임수정 API", description = "닉네임을 변경합니다.")
    @PutMapping("/nickname")
    public BaseResponse<String> updateNickName(@RequestBody MemberRequestDTO.UpdateNicknameDTO updateNicknameDTO){
        memberService.updateNickname(updateNicknameDTO);
        return BaseResponse.onSuccess("닉네임 변경 성공");
    }
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses({@ApiResponse(responseCode = "COMMON200", description = "수정 성공")})
    @Operation(summary = "비밀번호수정 API", description = "비밀번호를 변경합니다.")
    @PutMapping("/passwd")
    public BaseResponse<String> updatePasswd(@RequestBody MemberRequestDTO.UpdatePasswdDTO updatePasswdDTO){
        memberService.updatePasswd(updatePasswdDTO);
        return BaseResponse.onSuccess("비밀번호 변경 성공");
    }



}
