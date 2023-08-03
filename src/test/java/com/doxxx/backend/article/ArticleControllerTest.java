package com.doxxx.backend.article;

import com.doxxx.backend.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
@DisplayName("게시글 컨트롤러, 실패 테스트는 요청 값 검증")
public class ArticleControllerTest {

    @Autowired
    ArticleRepository articleRepository;


    @Nested
    @DisplayName("생성")
    class Create {

        @Test
        @DisplayName("성공")
        void createSuccess() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request);

            assertThat(response.statusCode()).isEqualTo(201);
        }

        @Test
        @DisplayName("실패 - 제목이 비어있음")
        void signUpFailByInvalidEmail() {
            final String title = "";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("제목이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 내용이 비어있음")
        void signUpFailByInvalidPassword() {
            final String title = "제목";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("내용이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 제목, 내용이 비어있음")
        void signUpFailByInvalidEmailAndPassword() {
            final String title = "";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("제목이 비어있습니다.", "내용이 비어있습니다.");
        }
    }
}
