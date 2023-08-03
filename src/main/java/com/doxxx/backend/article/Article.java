package com.doxxx.backend.article;

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

    protected Article() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Article(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
}
