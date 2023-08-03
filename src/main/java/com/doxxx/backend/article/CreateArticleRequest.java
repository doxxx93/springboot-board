package com.doxxx.backend.article;

import jakarta.validation.constraints.NotBlank;

public record CreateArticleRequest(@NotBlank(message = "제목이 비어있습니다.") String title,
                            @NotBlank(message = "내용이 비어있습니다.") String content) {
    public Article toEntity() {
        return new Article(title, content);
    }
}
