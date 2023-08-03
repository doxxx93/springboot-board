package com.doxxx.backend;

import lombok.Builder;

@Builder
public record RequestData(Object request, String path) {
}
