package com.app.demo.controller;

import com.app.demo.apiPayload.BaseResponse;
import com.app.demo.dto.MemberSignupDTO;
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
    @PostMapping("/sign-up")
    public BaseResponse<String> signUp(@RequestBody MemberSignupDTO memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return BaseResponse.onSuccess("회원가입 성공");
    }
}
