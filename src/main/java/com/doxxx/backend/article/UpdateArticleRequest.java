package com.doxxx.backend.article;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;

@Schema(description = "게시글 수정 요청")
public record UpdateArticleRequest(
        @Schema(description = "제목", example = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "제목이 비어있습니다.") String title,
        @Schema(description = "내용", example = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "내용이 비어있습니다.")
        String content) {
    public HashMap<String, String> makeBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("content", content);
        return body;
    }
}
