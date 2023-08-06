package com.doxxx.backend.article;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Articles", description = "게시글 API")
public interface ArticleSpecification {

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성하는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
    })
    @PostMapping("")
    ResponseEntity<CreateArticleResponse> create(@RequestBody @Valid CreateArticleRequest request);

    @Operation(summary = "게시글 목록 조회", description = "게시글의 목록을 가져오는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록이 성공적으로 반환되었습니다."),
    })
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "페이지 번호", name = "page", schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(in = ParameterIn.QUERY, description = "페이지 크기", name = "size", schema = @Schema(type = "integer", defaultValue = "15")),
            @Parameter(in = ParameterIn.QUERY, description = "정렬 방법", name = "sort", array = @ArraySchema(schema = @Schema(type = "string", allowableValues = {"id,desc", "id,asc"})))
    })
    @GetMapping("")
    ResponseEntity<GetArticleListResponse> list(@ParameterObject Pageable pageable);

    @Operation(summary = "게시글 조회", description = "특정 게시글을 가져오는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "404", description = "지정된 ID의 게시글을 찾을 수 없습니다.", content = @Content),
    })

    @GetMapping("/{id}")
    ResponseEntity<GetArticleResponse> get(@PathVariable Long id);

    @Operation(summary = "게시글 수정", description = "특정 게시글을 수정하는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글이 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "지정된 ID의 게시글을 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "403", description = "로그인한 사용자가 게시글의 저자가 아닙니다.", content = @Content),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
    })
    @PutMapping("/{id}")
    ResponseEntity<UpdateArticleResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateArticleRequest request);

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제하는 Endpoint입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "게시글이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "지정된 ID의 게시글을 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "403", description = "로그인한 사용자가 게시글의 저자가 아닙니다.", content = @Content),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
