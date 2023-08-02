package com.doxxx.backend.member;

import com.doxxx.backend.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() {
        given()
            .contentType(ContentType.JSON)
                .body(new SignUpRequest("test@test.com", "password1234"))
        .when()
                .post("members/signup")
        .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 형식이 아님")
    void signUpFailByInvalidEmail() {
        given()
                .contentType(ContentType.JSON)
                .body(new SignUpRequest("test", "password1234"))
        .when()
                .post("members/signup")
        .then()
                .statusCode(400)
                .body("message", equalTo("이메일은 @을 포함해야합니다."));
    }

    @Test
    @DisplayName("회원 가입 실패 - 비밀번호 형식이 아님")
    void signUpFailByInvalidPassword() {
        given()
                .contentType(ContentType.JSON)
                .body(new SignUpRequest("test@test.com", "1234"))
        .when()
                .post("members/signup")
        .then()
                .statusCode(400)
                .body("message", equalTo("비밀번호는 8자 이상이어야 합니다."));
    }
}
