package com.doxxx.backend;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class RestAssuredController {
    public static ExtractableResponse<Response> doPost(RequestData data) {
        return doRequest(Method.POST, data);
    }

    public static ExtractableResponse<Response> doGet(RequestData data) {
        return doRequest(Method.GET, data);
    }

    public static ExtractableResponse<Response> doPut(RequestData data) {
        return doRequest(Method.PUT, data);
    }

    public static ExtractableResponse<Response> doDelete(RequestData data) {
        return doRequest(Method.DELETE, data);
    }

    private static ExtractableResponse<Response> doRequest(Method method, RequestData data) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(data.request())
                .when()
                .request(method, data.path())
                .then().log().all().extract();
    }
}
