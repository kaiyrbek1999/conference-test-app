package com.example.conference.service;

import com.example.conference.dto.CreateUserRequestDto;
import com.example.conference.dto.UserDto;
import com.example.conference.exception.RoleException;
import com.example.conference.exception.UserException;

import java.util.List;

public interface UserService {

    void create(CreateUserRequestDto createUserRequestDto) throws RoleException;

    UserDto getUserById(Long id) throws UserException;

    void update(CreateUserRequestDto createUserRequestDto) throws UserException, RoleException;

    void delete(Long id);

    List<UserDto> getAllUsers();
}
