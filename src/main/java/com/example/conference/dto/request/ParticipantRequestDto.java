package com.example.conference.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantRequestDto {

    private Long conferenceId;
    private String participantEmail;
}
