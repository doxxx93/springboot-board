package com.doxxx.backend.article;

import com.doxxx.backend.ApiTest;
import com.doxxx.backend.member.MemberSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 컨트롤러 테스트, 실패 테스트는 요청 값 검증")
public class ArticleControllerTest extends ApiTest{

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
            final var response = ArticleSteps.게시글생성요청(request, accessToken);

            assertThat(response.statusCode()).isEqualTo(201);
        }

        @Test
        @DisplayName("실패 - 제목이 비어있음")
        void signUpFailByInvalidEmail() {
            final String title = "";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("제목이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 내용이 비어있음")
        void signUpFailByInvalidPassword() {
            final String title = "제목";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("내용이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 제목, 내용이 비어있음")
        void signUpFailByInvalidEmailAndPassword() {
            final String title = "";
            final String content = "";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            final var response = ArticleSteps.게시글생성요청(request, accessToken);

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
            ArticleSteps.게시글생성요청(request, accessToken);

            final String anotherTitle = "제목";
            final String anotherContent = "내용";
            final var anotherRequest = ArticleSteps.게시글생성요청_생성(anotherTitle, anotherContent);
            ArticleSteps.게시글생성요청(anotherRequest, accessToken);
        }

        @Test
        @DisplayName("성공")
        void findAllSuccess() {
            final var response = ArticleSteps.게시글리스트조회요청(Pageable.ofSize(10));
            long count = articleRepository.count();
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.jsonPath().getList("articleList").size()).isEqualTo((int) count);
        }
    }

    @Nested
    @DisplayName("조회")
    class FindById {
        @BeforeEach
        void setUp() {
            final String email = "test@test.com";
            final String password = "test1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));
            accessToken = MemberSteps.로그인요청(MemberSteps.로그인요청_생성(email, password)).jsonPath().getString("accessToken");
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);
            ArticleSteps.게시글생성요청(request, accessToken);

            final String anotherTitle = "제목";
            final String anotherContent = "내용";
            ArticleSteps.게시글생성요청_생성(anotherTitle, anotherContent);
        }

        @Test
        @DisplayName("성공")
        void findByIdSuccess() {
            final Long id = 1L;
            final var response = ArticleSteps.게시글조회요청(id);

            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.jsonPath().getString("title")).isEqualTo("제목");
            assertThat(response.jsonPath().getString("content")).isEqualTo("내용");
        }
    }

    @Nested
    @DisplayName("수정")
    class Update {
        @BeforeEach
        void setUp() {
            final String email = "test@test.com";
            final String password = "test1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));
            accessToken = MemberSteps.로그인요청(MemberSteps.로그인요청_생성(email, password)).jsonPath().getString("accessToken");
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);
            ArticleSteps.게시글생성요청(request, accessToken);
        }

        @Test
        @DisplayName("성공")
        void updateSuccess() {
            final Long id = 1L;
            final String title = "제목2";
            final String content = "내용2";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            final var response = ArticleSteps.게시글수정요청(id, request, accessToken);

            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.jsonPath().getLong("id")).isEqualTo(id);
        }

        @Test
        @DisplayName("실패 - 제목이 비어있음")
        void updateFailByInvalidEmail() {
            final Long id = 1L;
            final String title = "";
            final String content = "내용";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            final var response = ArticleSteps.게시글수정요청(id, request, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("제목이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 내용이 비어있음")
        void updateFailByInvalidPassword() {
            final Long id = 1L;
            final String title = "제목";
            final String content = "";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            final var response = ArticleSteps.게시글수정요청(id, request, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).isEqualTo("내용이 비어있습니다.");
        }

        @Test
        @DisplayName("실패 - 제목, 내용이 비어있음")
        void updateFailByInvalidEmailAndPassword() {
            final Long id = 1L;
            final String title = "";
            final String content = "";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            final var response = ArticleSteps.게시글수정요청(id, request, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.jsonPath().getString("message")).contains("제목이 비어있습니다.", "내용이 비어있습니다.");
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        @BeforeEach
        void setUp() {
            final String email = "test@test.com";
            final String password = "test1234";
            MemberSteps.회원가입요청(MemberSteps.회원가입요청_생성(email, password));
            accessToken = MemberSteps.로그인요청(MemberSteps.로그인요청_생성(email, password)).jsonPath().getString("accessToken");
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);
            ArticleSteps.게시글생성요청(request, accessToken);
        }

        @Test
        @DisplayName("성공")
        void deleteSuccess() {
            final Long id = 1L;
            final var response = ArticleSteps.게시글삭제요청(id, accessToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }
    }
}
