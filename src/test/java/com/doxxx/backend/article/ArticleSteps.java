package com.doxxx.backend.article;

import com.doxxx.backend.RequestData;
import com.doxxx.backend.RestAssuredController;
import com.doxxx.backend.dto.article.CreateArticleRequest;
import com.doxxx.backend.dto.article.UpdateArticleRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public class ArticleSteps {

    public static CreateArticleRequest 게시글생성요청_생성(final String title, final String content) {
        return new CreateArticleRequest(title, content);
    }

    public static ExtractableResponse<Response> 게시글생성요청(final CreateArticleRequest request, String accessToken) {
        return RestAssuredController.doPost(RequestData.builder()
                .accessToken(accessToken)
                .body(request.makeBody())
                .path("/articles").build());
    }


    public static ExtractableResponse<Response> 게시글리스트조회요청(Pageable pageable) {
        return RestAssuredController.doGet(RequestData.builder()
                .queryParams(
                        new HashMap<>() {
                            {
                                put("page", String.valueOf(pageable.getPageNumber()));
                                put("size", String.valueOf(pageable.getPageSize()));
                            }
                        }
                )
                .path("/articles").build());
    }

    public static ExtractableResponse<Response> 게시글조회요청(final Long id) {
        return RestAssuredController.doGet(RequestData.builder()
                .path("/articles/" + id).build());
    }

    public static UpdateArticleRequest 게시글수정요청_생성(final String title, final String content) {
        return new UpdateArticleRequest(title, content);
    }

    public static ExtractableResponse<Response> 게시글수정요청(final Long id, final UpdateArticleRequest request, String accessToken) {
        return RestAssuredController.doPut(RequestData.builder()
                .accessToken(accessToken)
                .body(request.makeBody())
                .path("/articles/" + id).build());
    }

    public static ExtractableResponse<Response> 게시글삭제요청(final Long id, String accessToken) {
        return RestAssuredController.doDelete(RequestData.builder()
                .accessToken(accessToken)
                .path("/articles/" + id).build());
    }
}
