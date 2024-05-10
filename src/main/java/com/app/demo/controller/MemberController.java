package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.dto.MemberSignupDTO;
import com.app.demo.dto.request.LoginRequestDTO;
import com.app.demo.dto.response.LoginResponseDTO;
import com.app.demo.dto.response.TokenRefreshResponse;
import com.app.demo.security.handler.annotation.ExtractToken;
import com.app.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
