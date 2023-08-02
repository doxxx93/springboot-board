package com.doxxx.backend.member;

import lombok.Builder;

@Builder
public record SignUpResponse(Long id, String email) {

    public static SignUpResponse of(Member member) {
        return SignUpResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .build();
    }
}
