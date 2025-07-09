package com.example.bookhub_back.controller.book;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.book.response.BookLogResponseDto;
import com.example.bookhub_back.service.book.BookLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_API + "/books/logs")
@RequiredArgsConstructor
public class BookLogController {
    private final BookLogService bookLogService;

    @GetMapping("/{isbn}")
    public ResponseEntity<ResponseDto<List<BookLogResponseDto>>> getLogsByBook(
            @PathVariable String isbn) {
        ResponseDto<List<BookLogResponseDto>> bookLog = bookLogService.getBookLogs(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(bookLog);
    }
}
