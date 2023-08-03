package com.doxxx.backend.article;

import com.doxxx.backend.RequestData;
import com.doxxx.backend.RestAssuredController;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

public class ArticleSteps {

    public static CreateArticleRequest 게시글생성요청_생성(final String title, final String content) {
        return new CreateArticleRequest(title, content);
    }

    public static ExtractableResponse<Response> 게시글생성요청(final CreateArticleRequest request, MultiValueMap<String, String> header) {
        return RestAssuredController.doPost(RequestData.builder()
                .header(header)
                .queryParams(new HashMap<>())
                .body(request.makeBody())
                .path("/articles").build());
    }

    public static GetArticleListRequest 게시글리스트조회요청_생성(final int page, final int size) {
        return new GetArticleListRequest(page, size);
    }

    public static ExtractableResponse<Response> 게시글리스트조회요청(final GetArticleListRequest request, MultiValueMap<String, String> header) {
        return RestAssuredController.doGet(RequestData.builder()
                .header(header)
                .body(new HashMap<>())
                .queryParams(request.makeQueryParams())
                .path("/articles").build());
    }
}
