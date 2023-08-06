package com.doxxx.backend.dto.article;

import com.doxxx.backend.article.Article;
import com.doxxx.backend.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;

@Schema(description = "게시글 생성 요청")
public record CreateArticleRequest(
        @NotBlank(message = "제목이 비어있습니다.")
        @Schema(description = "제목", example = "제목입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "내용", example = "내용입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "내용이 비어있습니다.")
        String content
) {
    public Article toEntity(Member member) {
        return new Article(title, content, member);
    }

    public HashMap<String, String> makeBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("content", content);
        return body;
    }
}
