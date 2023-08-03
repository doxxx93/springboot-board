package com.doxxx.backend.article;


import com.doxxx.backend.ApiTest;
import com.doxxx.backend.member.Member;
import com.doxxx.backend.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("게시글 서비스 테스트")
@ApiTest
public class ArticleServiceTest {

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
