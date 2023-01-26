package com.example.conference.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {

    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String role;
    private String password;
}


