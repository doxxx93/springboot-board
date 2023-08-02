package com.doxxx.backend.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED);
    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
