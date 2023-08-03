package com.doxxx.backend.article;

import lombok.Builder;

@Builder
public record GetArticleResponse(Long id, String title, String content, String author) {
    public static GetArticleResponse from(Article article) {
        return GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getMember().getEmail())
                .build();
    }
}
