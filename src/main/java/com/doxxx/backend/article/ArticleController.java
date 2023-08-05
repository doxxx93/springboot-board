package com.doxxx.backend.article;

import com.doxxx.backend.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticleController implements ArticleSpecification {

    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public ResponseEntity<CreateArticleResponse> create(@RequestBody @Valid CreateArticleRequest request) {
        return ResponseEntity.created(URI.create("/articles" + articleService.create(SecurityUtil.getCurrentMemberId(), request).id())).build();
    }

    @GetMapping("")
    public ResponseEntity<GetArticleListResponse> list(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(articleService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateArticleResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateArticleRequest request) {
        return ResponseEntity.ok(articleService.update(SecurityUtil.getCurrentMemberId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(SecurityUtil.getCurrentMemberId(), id);
        return ResponseEntity.noContent().build();
    }
}
