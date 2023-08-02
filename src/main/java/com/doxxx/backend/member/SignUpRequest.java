package com.doxxx.backend.member;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(@Pattern(regexp = ".*@.*", message = "이메일은 @을 포함해야합니다.") String email,
                            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.") String password) {
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
