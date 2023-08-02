package com.doxxx.backend.token;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken, String refreshToken, Long accessTokenExpiresIn) {
}
