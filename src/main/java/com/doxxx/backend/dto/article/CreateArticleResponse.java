package com.doxxx.backend.dto.article;

import com.doxxx.backend.article.Article;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 응답")
public record CreateArticleResponse(Long id) {

    public static CreateArticleResponse from(Article article) {
        return new CreateArticleResponse(article.getId());
    }
}
