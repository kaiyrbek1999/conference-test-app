package com.example.conference.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ConferenceDto {
    private Long id;
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Boolean isCancelled;
    private Integer maxSeats;
    private List<UserDto> participants;
}
