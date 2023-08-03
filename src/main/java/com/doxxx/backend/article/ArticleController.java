package com.doxxx.backend.article;

import com.doxxx.backend.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<CreateArticleResponse> create(@RequestBody @Valid CreateArticleRequest request) {
        return ResponseEntity.created(URI.create("/articles" + articleService.create(SecurityUtil.getCurrentMemberId(),request).id())).build();
    }
}
