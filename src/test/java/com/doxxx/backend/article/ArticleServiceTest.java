package com.doxxx.backend.article;


import com.doxxx.backend.ApiTest;
import com.doxxx.backend.member.Member;
import com.doxxx.backend.member.MemberRepository;
import com.doxxx.backend.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@DisplayName("게시글 서비스 테스트")
public class ArticleServiceTest extends ApiTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    MemberRepository memberRepository;

    @Nested
    @DisplayName("생성")
    class Create {
        @Test
        @DisplayName("성공")
        void createSuccess() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            Long memberId = newMemberId();

            final var response = articleService.create(memberId, request);

            assertThat(response).isNotNull();
        }

    }

    @Nested
    @DisplayName("리스트 조회")
    class FindAll {

        @Test
        @DisplayName("성공")
        void findAllSuccess() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            Long memberId = newMemberId();

            final var response = articleService.create(memberId, request);

            assertThat(response).isNotNull();
        }
    }

    @Nested
    @DisplayName("수정")
    class Update {
        Long memberId;
        Long articleId;

        @BeforeEach
        void setUp() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            memberId = newMemberId();
            articleId = articleService.create(memberId, request).id();
        }

        @Test
        @DisplayName("성공")
        @Transactional
        void updateSuccess() {
            final String title = "수정된 제목";
            final String content = "수정된 내용";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);
            final var response = articleService.update(memberId, articleId, request);

            assertThat(response).isNotNull();
        }

        @Test
        @DisplayName("실패 - 해당 게시글이 존재하지 않음")
        @Transactional
        void updateFailBecauseArticleNotFound() {
            final Long anotherArticleId = 100L;
            final String title = "수정된 제목";
            final String content = "수정된 내용";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> articleService.update(memberId, anotherArticleId, request))
                    .withMessage("해당 게시글을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("실패 - 자신의 게시글이 아님")
        @Transactional
        void updateFailBecauseNotMyArticle() {
            final Long anotherMemberId = newMemberId();
            final String title = "수정된 제목";
            final String content = "수정된 내용";
            final var request = ArticleSteps.게시글수정요청_생성(title, content);

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> articleService.update(anotherMemberId, articleId, request))
                    .withMessage("자신의 게시글만 수정할 수 있습니다.");
        }
    }

    @Nested
    @DisplayName("삭제")
    class Delete {
        Long memberId;
        Long articleId;

        @BeforeEach
        void setUp() {
            final String title = "제목";
            final String content = "내용";
            final var request = ArticleSteps.게시글생성요청_생성(title, content);

            memberId = newMemberId();
            articleId = articleService.create(memberId, request).id();
        }

        @Test
        @DisplayName("성공")
        @Transactional
        void deleteSuccess() {
            articleService.delete(memberId, articleId);
        }

        @Test
        @DisplayName("실패 - 해당 게시글이 존재하지 않음")
        @Transactional
        void deleteFailBecauseArticleNotFound() {
            final Long anotherArticleId = 100L;

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> articleService.delete(memberId, anotherArticleId))
                    .withMessage("해당 게시글을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("실패 - 자신의 게시글이 아님")
        @Transactional
        void deleteFailBecauseNotMyArticle() {
            final Long anotherMemberId = newMemberId();

            assertThatExceptionOfType(ApiException.class)
                    .isThrownBy(() -> articleService.delete(anotherMemberId, articleId))
                    .withMessage("자신의 게시글만 삭제할 수 있습니다.");
        }
    }

    private Long newMemberId() {
        final String email = "test@test.com";
        final String password = "test1234";
        Member member = Member.builder()
                .email(email)
                .password(password)
                .build();
        return memberRepository.save(member).getId();
    }
}
