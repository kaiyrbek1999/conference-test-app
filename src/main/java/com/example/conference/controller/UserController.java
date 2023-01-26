package com.example.conference.controller;

import com.example.conference.dto.CreateUserRequestDto;
import com.example.conference.dto.UserDto;
import com.example.conference.dto.response.ResultResponseDto;
import com.example.conference.exception.RoleException;
import com.example.conference.exception.UserException;
import com.example.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.conference.constants.Constants.OK;
import static com.example.conference.constants.Constants.API_USER;

@RestController
@Validated
@RequestMapping(API_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws UserException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResultResponseDto> create(@RequestBody CreateUserRequestDto user) throws RoleException {
        userService.create(user);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResultResponseDto> update(@RequestBody CreateUserRequestDto user) throws UserException, RoleException {
        userService.update(user);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResultResponseDto> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }
}