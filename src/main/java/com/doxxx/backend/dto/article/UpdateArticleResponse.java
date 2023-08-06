package com.doxxx.backend.dto.article;

import com.doxxx.backend.article.Article;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 수정 응답")
public record UpdateArticleResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long id
) {
    public static UpdateArticleResponse from(Article article) {
        return new UpdateArticleResponse(article.getId());
    }
}
