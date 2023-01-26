package com.example.conference.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {

    private String status;
    private String description;
}
