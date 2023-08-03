package com.doxxx.backend.article;

public record UpdateArticleResponse(Long id) {
    public static UpdateArticleResponse from(Article article) {
        return new UpdateArticleResponse(article.getId());
    }
}
