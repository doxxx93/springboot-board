package com.doxxx.backend.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MemberSteps {

    public static ExtractableResponse<Response> 회원가입요청(final SignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/members/signup")
                .then()
                .log().all().extract();
    }

    public static SignUpRequest 회원가입요청_생성(final String email, final String password) {
        return new SignUpRequest(email, password);
    }

    public static ExtractableResponse<Response> 로그인요청(final SignInRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/members/signin")
                .then()
                .log().all().extract();
    }

    public static SignInRequest 로그인요청_생성(final String email, final String password) {
        return new SignInRequest(email, password);
    }
}
