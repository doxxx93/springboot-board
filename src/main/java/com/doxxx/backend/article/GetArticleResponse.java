package com.doxxx.backend.article;

import lombok.Builder;

@Builder
public record GetArticleResponse(Long id, String title, String content, Long authorId) {
    public static GetArticleResponse from(Article article) {
        return GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getMember().getId())
                .build();
    }
}
