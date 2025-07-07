package com.example.bookhub_back.dto.alert.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponseDto {
    private Long alertId;
    private String alertType;
    private String message;
    private String alertTargetTable;
    private Long targetPk;
    private String targetIsbn;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
