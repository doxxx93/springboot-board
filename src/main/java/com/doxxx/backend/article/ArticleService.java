package com.doxxx.backend.article;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public CreateArticleResponse create(CreateArticleRequest request) {
        Article article = articleRepository.save(request.toEntity());
        return CreateArticleResponse.of(article);
    }
}
