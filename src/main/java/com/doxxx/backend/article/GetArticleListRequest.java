
package com.doxxx.backend.article;

import java.util.HashMap;

public record GetArticleListRequest(int page, int size) {
    public HashMap<String, String> makeQueryParams() {
        final var queryParams = new HashMap<String, String>();
        queryParams.put("page", String.valueOf(page));
        queryParams.put("size", String.valueOf(size));
        return queryParams;
    }
}
