package com.doxxx.backend.dto.article;

import com.doxxx.backend.article.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "게시글 목록 조회 응답")
public record GetArticleListResponse(
        @Schema(description = "게시글 목록")
        List<GetArticleResponse> articleList) {

    public static GetArticleListResponse from(Page<Article> articles) {
        return new GetArticleListResponse(articles.stream()
                .map(GetArticleResponse::from)
                .toList());
    }
}
