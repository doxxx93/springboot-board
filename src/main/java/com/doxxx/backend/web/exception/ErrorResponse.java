package com.doxxx.backend.web.exception;


import lombok.Builder;

@Builder
public record ErrorResponse(ErrorCode code, String message) {
}
