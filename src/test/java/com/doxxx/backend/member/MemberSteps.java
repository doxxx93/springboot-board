package com.doxxx.backend.member;

import com.doxxx.backend.RequestData;
import com.doxxx.backend.RestAssuredController;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberSteps {

    public static ExtractableResponse<Response> 회원가입요청(final SignUpRequest request) {
        return RestAssuredController.doPost(RequestData.builder()
                .body(request.makeBody())
                .path("/members/signup").build());
    }

    public static SignUpRequest 회원가입요청_생성(final String email, final String password) {
        return new SignUpRequest(email, password);
    }

    public static ExtractableResponse<Response> 로그인요청(final SignInRequest request) {
        return RestAssuredController.doPost(RequestData.builder()
                .body(request.makeBody())
                .path("/members/signin").build());
    }

    public static SignInRequest 로그인요청_생성(final String email, final String password) {
        return new SignInRequest(email, password);
    }
}
