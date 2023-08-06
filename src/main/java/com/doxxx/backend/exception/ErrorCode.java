package com.doxxx.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND),
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT);
    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
