package com.doxxx.backend;

import lombok.Builder;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

@Builder
public record RequestData(MultiValueMap<String, String> header, HashMap<String, String> body,
                          HashMap<String, String> queryParams, String path) {
}
