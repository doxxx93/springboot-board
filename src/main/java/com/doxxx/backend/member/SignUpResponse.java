package com.doxxx.backend.member;

public record SignUpResponse(Long id, String email) {

    public static SignUpResponse from(Member member) {
        return new SignUpResponse(member.getId(), member.getEmail());
    }
}
