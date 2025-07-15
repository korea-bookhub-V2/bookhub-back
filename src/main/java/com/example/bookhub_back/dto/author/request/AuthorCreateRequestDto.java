package com.example.bookhub_back.dto.author.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthorCreateRequestDto {
    @NotEmpty
    private List<AuthorRequestDto> authors;
}
