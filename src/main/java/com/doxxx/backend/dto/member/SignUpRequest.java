package com.doxxx.backend.dto.member;

import com.doxxx.backend.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        @Schema(description = "이메일", example = "test@test.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(regexp = ".*@.*", message = "이메일은 @을 포함해야합니다.")
        String email,
        @Schema(description = "비밀번호", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password) {
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }

    public HashMap<String, String> makeBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
