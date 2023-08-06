package com.doxxx.backend.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "토큰 발급 응답")
public record TokenResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjI5MjUwNjY2LCJleHAiOjE2MjkzMTcxNjZ9.8")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjI5MjUwNjY2LCJleHAiOjE2MjkzMTcxNjZ9.8")
        String refreshToken,
        @Schema(description = "액세스 토큰 만료 시간", example = "3600")
        Long accessTokenExpiresIn) {
}
