package com.doxxx.backend.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "게시글 조회 응답")
public record GetArticleResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long id,
        @Schema(description = "게시글 제목", example = "제목")
        String title,
        @Schema(description = "게시글 내용", example = "내용")
        String content,
        @Schema(description = "게시글 작성자 ID", example = "1")
        Long authorId) {
    public static GetArticleResponse from(Article article) {
        return GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getMember().getId())
                .build();
    }
}
