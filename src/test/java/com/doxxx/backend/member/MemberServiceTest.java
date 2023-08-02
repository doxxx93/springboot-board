package com.doxxx.backend.member;

import com.doxxx.backend.DatabaseCleanup;
import com.doxxx.backend.web.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayName("회원 가입")
    class SignUp {

        @Test
        @DisplayName("회원 가입 성공")
        void signUp() {
            final SignUpRequest request = new SignUpRequest("test@test.com", "password1234");
            final SignUpResponse response = memberService.signUp(request);

            assertThat(response.email()).isEqualTo("test@test.com").describedAs("회원 가입 성공");
        }

        @Test
        @DisplayName("회원 가입 실패 - 이미 가입한 회원")
        void signUpFailByDuplicatedEmail() {
            final SignUpRequest request = new SignUpRequest("test@test.com", "password1234");
            memberService.signUp(request);

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> memberService.signUp(request))
                    .withMessage("이미 존재하는 유저입니다. email: " + request.email());
        }
    }
}