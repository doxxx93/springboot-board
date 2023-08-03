package com.doxxx.backend.article;

import com.doxxx.backend.ApiTest;
import com.doxxx.backend.member.MemberSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
@DisplayName("게시글 컨트롤러, 실패 테스트는 요청 값 검증")
public class ArticleControllerTest {

    @Autowired
    ArticleRepository articleRepository;

    @Nested
    @DisplayName("생성")
    class Create {

        String accessToken;

        @BeforeEach
        void setUp() {
            final String email = "test@test.com";
            final String password = "test1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));

            accessToken = MemberSteps.로그인요청(MemberSteps.로그인요청_생성(email, password)).jsonPath().getString("accessToken");
        }

        @Test
        @DisplayName("성공")
        void createSuccess() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);
            final var response = ArticleSteps.게시글생성요청(request, getHeader());

            assertThat(response.statusCode()).isEqualTo(201);
        }

        @Test
        @DisplayName("실패 - 제목이 비어있음")
        void signUpFailByInvalidEmail() {
            final String title = "";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, getHeader());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("제목이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 내용이 비어있음")
        void signUpFailByInvalidPassword() {
            final String title = "제목";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, getHeader());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("내용이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 제목, 내용이 비어있음")
        void signUpFailByInvalidEmailAndPassword() {
            final String title = "";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, getHeader());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("제목이 비어있습니다.", "내용이 비어있습니다.");
        }

        private MultiValueMap<String, String> getHeader() {
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            header.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            return header;
        }
    }
}
