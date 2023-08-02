package com.doxxx.backend.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT),
    BAD_REQUEST(HttpStatus.BAD_REQUEST);
    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}