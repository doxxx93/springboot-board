package com.doxxx.backend.article;

import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;

public record UpdateArticleRequest(@NotBlank(message = "제목이 비어있습니다.") String title,
                                   @NotBlank(message = "내용이 비어있습니다.") String content) {
    public HashMap<String, String> makeBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("content", content);
        return body;
    }
}
