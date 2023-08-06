package com.doxxx.backend.member;

import com.doxxx.backend.token.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Members", description = "회원 API")
public interface MemberSpecification {

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록하는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 가입이 성공적으로 이루어졌습니다."),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일을 가진 회원입니다.", content = @Content),
    })
    @PostMapping("/signup")
    ResponseEntity<SignUpResponse> signup(@RequestBody @Valid SignUpRequest request);

    @Operation(summary = "회원 로그인", description = "회원 로그인을 위한 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인이 성공적으로 이루어졌습니다."),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호를 잘못 입력했습니다.", content = @Content),
    })
    @PostMapping("/signin")
    ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest request);
}
