package com.doxxx.backend.article;

import com.doxxx.backend.member.Member;
import com.doxxx.backend.member.MemberService;
import com.doxxx.backend.web.exception.ApiException;
import com.doxxx.backend.web.exception.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    ArticleRepository articleRepository;
    MemberService memberService;

    public ArticleService(ArticleRepository articleRepository, MemberService memberService) {
        this.articleRepository = articleRepository;
        this.memberService = memberService;
    }

    public CreateArticleResponse create(Long memberId, CreateArticleRequest request) {
        Member member = memberService.findById(memberId);
        Article article = articleRepository.save(request.toEntity(member));
        return CreateArticleResponse.from(article);
    }

    public GetArticleListResponse list(Pageable pageable) {
        return GetArticleListResponse.from(articleRepository.findAll(pageable));
    }

    public GetArticleResponse get(Long id) {
        Article article = getArticleById(id);
        return GetArticleResponse.from(article);
    }

    public UpdateArticleResponse update(Long memberId, Long articleId, UpdateArticleRequest request) {
        Article article = getArticleById(articleId);
        Member member = memberService.findById(memberId);
        validateAuthor(article, member, "자신의 게시글만 수정할 수 있습니다.");
        article.update(request);
        articleRepository.save(article);
        return UpdateArticleResponse.from(article);
    }

    public void delete(Long memberId, Long articleId) {
        Article article = getArticleById(articleId);
        Member member = memberService.findById(memberId);
        validateAuthor(article, member, "자신의 게시글만 삭제할 수 있습니다.");
        articleRepository.delete(article);
    }

    private static void validateAuthor(Article article, Member member, String message) {
        if (!article.isAuthor(member)) {
            throw new ApiException(ErrorCode.FORBIDDEN, message);
        }
    }

    private Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_FOUND, "해당 게시글을 찾을 수 없습니다."));
    }
}
