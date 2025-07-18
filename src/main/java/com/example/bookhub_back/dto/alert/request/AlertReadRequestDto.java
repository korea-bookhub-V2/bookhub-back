package com.example.bookhub_back.dto.alert.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertReadRequestDto {
    private List<Long> alertIds;
}
