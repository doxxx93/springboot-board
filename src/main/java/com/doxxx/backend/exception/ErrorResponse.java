package com.doxxx.backend.exception;


import lombok.Builder;

@Builder
public record ErrorResponse(ErrorCode code, String message) {
}
