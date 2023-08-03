package com.doxxx.backend.article;

import com.doxxx.backend.RequestData;
import com.doxxx.backend.RestAssuredController;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ArticleSteps {

    public static CreateArticleRequest 게시글생성요청_생성(final String title, final String content) {
        return new CreateArticleRequest(title, content);
    }

    public static ExtractableResponse<Response> 게시글생성요청(final CreateArticleRequest request, MultiValueMap<String, String> header) {
        return RestAssuredController.doPost(RequestData.builder()
                .header(header)
                .body(request.makeBody())
                .path("/articles").build());
    }

    public static GetArticleListRequest 게시글리스트조회요청_생성(final int page, final int size) {
        return new GetArticleListRequest(page, size);
    }

    public static ExtractableResponse<Response> 게시글리스트조회요청(final GetArticleListRequest request, MultiValueMap<String, String> header) {
        return RestAssuredController.doGet(RequestData.builder()
                .header(header)
                .queryParams(request.makeQueryParams())
                .path("/articles").build());
    }

    public static MultiValueMap<String, String> authorizationHeader(String accessToken) {
        MultiValueMap<String, String> header = getDefaultHeader();
        header.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return header;
    }

    public static MultiValueMap<String, String> getDefaultHeader() {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return header;
    }
}
