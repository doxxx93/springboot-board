package com.doxxx.backend.article;

import org.springframework.data.domain.Page;

import java.util.List;

public record GetArticleListResponse(List<GetArticleResponse> articleList) {

    public static GetArticleListResponse from(Page<Article> articles) {
        return new GetArticleListResponse(articles.stream()
                .map(GetArticleResponse::from)
                .toList());
    }
}
