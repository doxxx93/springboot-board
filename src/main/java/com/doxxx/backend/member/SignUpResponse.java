package com.doxxx.backend.member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")
public record SignUpResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,
        @Schema(description = "회원 이메일", example = "test@test.com")
        String email) {

    public static SignUpResponse from(Member member) {
        return new SignUpResponse(member.getId(), member.getEmail());
    }
}
