package com.example.bookhub_back.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;
}
