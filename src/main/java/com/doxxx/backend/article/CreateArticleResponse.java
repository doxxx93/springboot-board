package com.doxxx.backend.article;

public record CreateArticleResponse(Long id) {

    public static CreateArticleResponse of(Article article) {
        return new CreateArticleResponse(article.getId());
    }
}
