package com.example.bookhub_back.controller.book;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.PageResponseDto;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.book.response.BookLogResponseDto;
import com.example.bookhub_back.service.book.BookLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/books/logs")
@RequiredArgsConstructor
@Tag(name = "BookLog API", description = "책 로그 API입니다.")

public class BookLogController {
    private final BookLogService bookLogService;

    @Operation(summary = "책 로그 조회", description = "책 로그를 isbn으로 조회합니다.")
    @GetMapping("/{isbn}")
    public ResponseEntity<ResponseDto<PageResponseDto<BookLogResponseDto>>> getLogsByBook(
            @PathVariable String isbn,
            @RequestParam int page,
            @RequestParam int size) {
        ResponseDto<PageResponseDto<BookLogResponseDto>> bookLog = bookLogService.getBookLogs(isbn, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(bookLog);
    }
}
