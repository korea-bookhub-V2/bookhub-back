package com.example.bookhub_back.controller.book;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.book.request.BookCreateRequestDto;
import com.example.bookhub_back.dto.book.request.BookUpdateRequestDto;
import com.example.bookhub_back.dto.book.response.BookResponseDto;
import com.example.bookhub_back.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping(ApiMappingPattern.ADMIN_API + "/books")
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook(
            @RequestHeader("Authorization") String token,
            @RequestPart("dto") BookCreateRequestDto dto,
            @RequestPart(value = "coverImageFile", required = false) MultipartFile coverImageFile) throws Exception {
     ResponseDto<BookResponseDto> book = bookService.createBook(dto, token, coverImageFile);
     return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping(ApiMappingPattern.ADMIN_API + "/books/{isbn}")
    public ResponseDto<BookResponseDto> updateBook(
            @PathVariable String isbn,
            @RequestHeader("Authorization") String token,
            @RequestPart BookUpdateRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile newCoverImageFile) throws Exception {
        return bookService.updateBook(isbn, dto, token, newCoverImageFile);
    }

    @PutMapping(ApiMappingPattern.ADMIN_API + "/books/hidden/{isbn}")
    public ResponseDto<Void> hideBook(
            @PathVariable String isbn,
            @RequestHeader("Authorization") String token) {
        return bookService.hideBook(isbn, token);
    }

    @GetMapping(ApiMappingPattern.COMMON_API + "/books/search")
    public ResponseDto<List<BookResponseDto>> searchBook(
            @RequestParam String keyword) {
        return bookService.searchBook(keyword);
    }
}
