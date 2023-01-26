package com.example.conference.handler;

import com.example.conference.controller.ConferenceController;
import com.example.conference.controller.UserController;
import com.example.conference.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.conference.constants.Constants.ERROR;

@ControllerAdvice(basePackageClasses = {ConferenceController.class, UserController.class})
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponseDto> handleValidationFailure(Exception ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus(ERROR);
        errorResponseDto.setDescription(ex.getMessage());
        log.error("Handle exception with description: {}", errorResponseDto.getDescription());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
}