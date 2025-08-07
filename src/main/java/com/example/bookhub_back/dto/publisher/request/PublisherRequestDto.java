package com.example.bookhub_back.dto.publisher.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequestDto {
    @NotNull(message = "publisherName is Mandatory")
    private String publisherName;
}
