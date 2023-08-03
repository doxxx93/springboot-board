package com.doxxx.backend.member;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;

public record SignInRequest(@Pattern(regexp = ".*@.*", message = "이메일은 @을 포함해야합니다.") String email,
                            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.") String password) {
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
