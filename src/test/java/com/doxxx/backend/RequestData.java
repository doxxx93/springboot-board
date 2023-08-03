package com.doxxx.backend;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

@Builder
public record RequestData(MultiValueMap<String, String> header, String accessToken, HashMap<String, String> body,
                          HashMap<String, String> queryParams, String path) {

    public RequestData {
        if (header == null || accessToken != null) {
            MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
            headerMap.addAll(DEFAULT_HEADER);
            if (accessToken != null) {
                headerMap.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            }
            header = headerMap;
        }
        if (body == null) {
            body = EMPTY_BODY;
        }
        if (queryParams == null) {
            queryParams = EMPTY_QUERY_PARAMS;
        }
    }

    public static final MultiValueMap<String, String> DEFAULT_HEADER = new LinkedMultiValueMap<>() {
        {
            add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
    };
    public static final HashMap<String, String> EMPTY_BODY = new HashMap<>();
    public static final HashMap<String, String> EMPTY_QUERY_PARAMS = new HashMap<>();
}
