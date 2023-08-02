package com.doxxx.backend.member;

import com.doxxx.backend.DatabaseCleanup;
import com.doxxx.backend.jwt.TokenProvider;
import com.doxxx.backend.token.TokenResponse;
import com.doxxx.backend.web.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@DisplayName("회원 서비스 테스트")
class MemberServiceTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @Nested
    @DisplayName("회원 가입")
    class SignUp {

        @Test
        @DisplayName("성공")
        void signUp() {
            final String email = "test@test.com";
            final String password = "password1234";
            final SignUpRequest request = MemberSteps.회원가입요청_생성(email, password);

            final SignUpResponse response = memberService.signUp(request);

            assertThat(response.email())
                    .isEqualTo("test@test.com")
                    .describedAs("회원 가입 성공");
        }
        @Test
        @DisplayName("회원 가입 실패 - 이미 가입한 회원")
        void signUpFailByDuplicatedEmail() {
            final String email = "test@test.com";
            final String password = "password1234";
            final SignUpRequest request = MemberSteps.회원가입요청_생성(email, password);

            memberService.signUp(request);
            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> memberService.signUp(request))
                    .withMessage("이미 존재하는 유저입니다. email: " + request.email());
        }
    }

    @Nested
    @DisplayName("로그인")
    class SignIn {

        @BeforeEach
        @DisplayName("유저 생성")
        void setUpUser() {
            final String email = "test@test.com";
            final String password = "password1234";
            final SignUpRequest request = MemberSteps.회원가입요청_생성(email, password);
            memberService.signUp(request);
            memberRepository.flush();
        }

        @Test
        @DisplayName("로그인 성공")
        void signIn() {
            final String email = "test@test.com";
            final String password = "password1234";
            SignInRequest signInRequest = MemberSteps.로그인요청_생성(email, password);
            TokenResponse tokenResponse = memberService.signIn(signInRequest);

            Authentication authentication = tokenProvider.getAuthentication(tokenResponse.accessToken());
            long memberId = Long.parseLong(authentication.getName());

            Member member = memberRepository.findByEmail(email).get();
            assertThat(memberId).isEqualTo(member.getId());
        }

        @Test
        @DisplayName("로그인 실패 - 이메일이나 비밀번호가 틀린 경우")
        void signInFailByWrongEmail() {
            final String email = "test@test.com";
            final String password = "password34";
            SignInRequest signInRequest = MemberSteps.로그인요청_생성(email, password);

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> memberService.signIn(signInRequest))
                    .withMessage("이메일 또는 비밀번호를 잘못 입력했습니다.");
        }

        @Test
        @DisplayName("로그인 실패 - 이메일이나 비밀번호가 틀린 경우")
        void signInFailByWrongPassword() {
            final String email = "test@test.net";
            final String password = "password34";
            SignInRequest signInRequest = MemberSteps.로그인요청_생성(email, password);

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> memberService.signIn(signInRequest))
                    .withMessage("이메일 또는 비밀번호를 잘못 입력했습니다.");
        }
    }
}
