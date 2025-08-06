package com.example.bookhub_back.controller.book;

import com.example.bookhub_back.common.constants.ApiMappingPattern;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.book.request.BookCreateRequestDto;
import com.example.bookhub_back.dto.book.request.BookUpdateRequestDto;
import com.example.bookhub_back.dto.book.response.BookResponseDto;
import com.example.bookhub_back.security.auth.EmployeePrincipal;
import com.example.bookhub_back.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Book API", description = "책 관련 API입니다.")

public class BookController {
    private final BookService bookService;

    @Operation(summary = "책 등록", description = "책을 등록합니다.")
    @PostMapping(ApiMappingPattern.ADMIN_API + "/books")
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @RequestPart("dto") BookCreateRequestDto dto,
            @RequestPart(value = "coverImageFile", required = false) MultipartFile coverImageFile) throws Exception {
        Long employeeId = employeePrincipal.getEmployeeId();
        ResponseDto<BookResponseDto> book = bookService.createBook(dto, employeeId, coverImageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Operation(summary = "책 수정", description = "책 정보를 수정합니다.")
    @PutMapping(ApiMappingPattern.ADMIN_API + "/books/{isbn}")
    public ResponseDto<BookResponseDto> updateBook(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @PathVariable String isbn,
            @RequestPart BookUpdateRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile newCoverImageFile) throws Exception {
        Long employeeId = employeePrincipal.getEmployeeId();
        return bookService.updateBook(isbn, dto, employeeId, newCoverImageFile);
    }

    @Operation(summary = "책 숨김", description = "책을 숨김 처리합니다.")
    @PutMapping(ApiMappingPattern.ADMIN_API + "/books/hidden/{isbn}")
    public ResponseDto<Void> hideBook(
            @AuthenticationPrincipal EmployeePrincipal employeePrincipal,
            @PathVariable String isbn) {
        Long employeeId = employeePrincipal.getEmployeeId();
        return bookService.hideBook(isbn, employeeId);
    }

    @Operation(summary = "책 검색", description = "책을 키워드(isbn, 제목, 저자, 출판사, 카테고리)로 검색합니다.")
    @GetMapping(ApiMappingPattern.COMMON_API + "/books/search")
    public ResponseDto<List<BookResponseDto>> searchBook(
            @RequestParam String keyword) {
        return bookService.searchBook(keyword);
    }
}
