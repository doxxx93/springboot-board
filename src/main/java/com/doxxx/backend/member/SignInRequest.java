package com.doxxx.backend.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;

@Schema(description = "로그인 요청")
public record SignInRequest(
        @Schema(description = "이메일", example = "test@test.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(regexp = ".*@.*", message = "이메일은 @을 포함해야합니다.")
        String email,
        @Schema(description = "비밀번호", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password) {
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public HashMap<String, String> makeBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
