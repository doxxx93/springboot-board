package com.doxxx.backend.member;

import com.doxxx.backend.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 컨트롤러 테스트, 실패 테스트는 요청 값 검증")
class MemberControllerTest extends ApiTest {

    @Autowired
    MemberRepository memberRepository;

    @Nested
    @DisplayName("회원가입")
    class SignUp {
        @Test
        @DisplayName("성공")
        void signUpSuccess() {
            final String email = "test@test.com";
            final String password = "test1234";
            final var request = MemberSteps.회원가입요청_생성(email, password);

            final var response = MemberSteps.회원가입요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }

        @Test
        @DisplayName("실패 - 이메일 형식이 아님")
        void signUpFailByInvalidEmail() {
            final String email = "test";
            final String password = "password1234";
            final var request = MemberSteps.회원가입요청_생성(email, password);

            final var response = MemberSteps.회원가입요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("이메일은 @을 포함해야합니다.");
        }

        @Test
        @DisplayName("실패 - 비밀번호 형식이 아님")
        void signUpFailByInvalidPassword() {
            final String email = "test@test.com";
            final String password = "1234";
            final var request = MemberSteps.회원가입요청_생성(email, password);

            final var response = MemberSteps.회원가입요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("비밀번호는 8자 이상이어야 합니다.");
        }

        @Test
        @DisplayName("실패 - 이메일, 비밀번호 모두 형식이 아님")
        void signUpFailByInvalidEmailAndPassword() {
            final String email = "testtest.com";
            final String password = "1234";
            final var request = MemberSteps.회원가입요청_생성(email, password);

            final var response = MemberSteps.회원가입요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("이메일은 @을 포함해야합니다.", "비밀번호는 8자 이상이어야 합니다.");

        }
    }

    @Nested
    @DisplayName("로그인")
    class SignIn {

        @Test
        @DisplayName("로그인 성공")
        void signInSuccess() {
            final String email = "test@test.com";
            final String password = "password1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));

            final var request = MemberSteps.로그인요청_생성(email, password);

            final var response = MemberSteps.로그인요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("실패 - 이메일 형식이 아님")
        void signInFailByInvalidEmail() {
            final String email = "test";
            final String password = "password1234";
            final var request = MemberSteps.로그인요청_생성(email, password);

            final var response = MemberSteps.로그인요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("이메일은 @을 포함해야합니다.");
        }

        @Test
        @DisplayName("실패 - 비밀번호 형식이 아님")
        void signInFailByInvalidPassword() {
            final String email = "test@test.com";
            final String password = "1234";
            final var request = MemberSteps.로그인요청_생성(email, password);

            final var response = MemberSteps.로그인요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("비밀번호는 8자 이상이어야 합니다.");
        }

        @Test
        @DisplayName("실패 - 이메일, 비밀번호 모두 형식이 아님")
        void signUpFailByInvalidEmailAndPassword() {
            final String email = "testtest.com";
            final String password = "1234";
            final var request = MemberSteps.로그인요청_생성(email, password);

            final var response = MemberSteps.로그인요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("이메일은 @을 포함해야합니다.", "비밀번호는 8자 이상이어야 합니다.");
        }
    }
}
