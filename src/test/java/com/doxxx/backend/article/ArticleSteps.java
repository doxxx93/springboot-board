package com.doxxx.backend.article;

import com.doxxx.backend.RequestData;
import com.doxxx.backend.RestAssuredController;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ArticleSteps {

    public static CreateArticleRequest 게시글생성요청_생성(final String title, final String content) {
        return new CreateArticleRequest(title, content);
    }

    public static ExtractableResponse<Response> 게시글생성요청(final CreateArticleRequest request) {
        return RestAssuredController.doPost(RequestData.builder()
                .request(request)
                .path("/articles").build());
    }
}
