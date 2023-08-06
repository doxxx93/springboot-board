package com.doxxx.backend.article;

import com.doxxx.backend.dto.article.UpdateArticleRequest;
import com.doxxx.backend.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Article() {
    }

    public Article(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public boolean isAuthor(Member member) {
        return this.member.equals(member);
    }

    public void update(UpdateArticleRequest request) {
        this.title = request.title();
        this.content = request.content();
    }
}
