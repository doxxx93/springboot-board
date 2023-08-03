package com.doxxx.backend.article;

import com.doxxx.backend.ApiTest;
import com.doxxx.backend.member.MemberSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
@DisplayName("게시글 컨트롤러 테스트, 실패 테스트는 요청 값 검증")
public class ArticleControllerTest {

    @Autowired
    ArticleRepository articleRepository;
    String accessToken;

    @Nested
    @DisplayName("생성")
    class Create {

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
            final var response = ArticleSteps.게시글생성요청(request, ArticleSteps.authorizationHeader(accessToken));

            assertThat(response.statusCode()).isEqualTo(201);
        }

        @Test
        @DisplayName("실패 - 제목이 비어있음")
        void signUpFailByInvalidEmail() {
            final String title = "";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, ArticleSteps.authorizationHeader(accessToken));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("제목이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 내용이 비어있음")
        void signUpFailByInvalidPassword() {
            final String title = "제목";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, ArticleSteps.authorizationHeader(accessToken));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("내용이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 제목, 내용이 비어있음")
        void signUpFailByInvalidEmailAndPassword() {
            final String title = "";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, ArticleSteps.authorizationHeader(accessToken));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("제목이 비어있습니다.", "내용이 비어있습니다.");
        }

    }

    @Nested
    @DisplayName("리스트 조회")
    class FindAll {

        @BeforeEach
        void setUp() {
            final String email = "test@test.com";
            final String password = "test1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));
            accessToken = MemberSteps.로그인요청(MemberSteps.로그인요청_생성(email, password)).jsonPath().getString("accessToken");
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);
            ArticleSteps.게시글생성요청(request, ArticleSteps.authorizationHeader(accessToken));

            final String anotherTitle = "제목";
            final String anotherContent = "내용";
            final var anotherRequest = ArticleSteps.게시글생성요청_생성(anotherTitle, anotherContent);
            ArticleSteps.게시글생성요청(anotherRequest, ArticleSteps.authorizationHeader(accessToken));
            for (int i = 0; i < 15; i++) {
                ArticleSteps.게시글생성요청(anotherRequest, ArticleSteps.authorizationHeader(accessToken));
            }
        }

        @Test
        @DisplayName("성공")
        void findAllSuccess() {
            final int page = 1;
            final int size = 10;
            final var request = ArticleSteps.게시글리스트조회요청_생성(page, size);

            final var response = ArticleSteps.게시글리스트조회요청(request);
            long count = articleRepository.count();
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.jsonPath().getList("articleList").size()).isEqualTo(count % size);
        }
    }
}
