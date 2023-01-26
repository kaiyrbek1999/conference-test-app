package com.example.conference.controller;

import com.example.conference.dto.request.ParticipantRequestDto;
import com.example.conference.dto.ConferenceDto;
import com.example.conference.dto.response.ConferenceStatusResponseDto;
import com.example.conference.dto.response.ResultResponseDto;
import com.example.conference.exception.ConferenceException;
import com.example.conference.exception.UserException;
import com.example.conference.model.User;
import com.example.conference.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.conference.constants.Constants.API_CONFERENCE;
import static com.example.conference.constants.Constants.OK;

@RestController
@Validated
@RequestMapping(API_CONFERENCE)
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<List<ConferenceDto>> getAllConferences(@AuthenticationPrincipal User user) throws UserException {
        return ResponseEntity.ok(conferenceService.getAll(user));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultResponseDto> create(@RequestBody ConferenceDto conferenceDto, @AuthenticationPrincipal User user) {
        conferenceService.create(conferenceDto, user);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ConferenceDto> getById(@PathVariable(value = "id") Long id) throws UserException, ConferenceException {
        return ResponseEntity.ok(conferenceService.getById(id));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ResultResponseDto> update(@RequestBody ConferenceDto conferenceDto) throws ConferenceException {
        conferenceService.update(conferenceDto);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ResultResponseDto> delete(@PathVariable Long id) throws ConferenceException {
        conferenceService.delete(id);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "/add-participant", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ResultResponseDto> addParticipant(@RequestBody ParticipantRequestDto request) throws UserException, ConferenceException {
        conferenceService.addParticipant(request.getConferenceId(), request.getParticipantEmail());
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "/remove-participant", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ResultResponseDto> removeParticipant(@RequestBody ParticipantRequestDto request) throws UserException, ConferenceException {
        conferenceService.removeParticipant(request.getConferenceId(), request.getParticipantEmail());
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ResultResponseDto> cancel(@PathVariable Long id) throws ConferenceException {
        conferenceService.cancel(id);
        return ResponseEntity.ok(new ResultResponseDto(OK));
    }

    @RequestMapping(value = "/check-availability/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONFERENCE_OWNER')")
    public ResponseEntity<ConferenceStatusResponseDto> checkAvailability(@PathVariable Long id) throws UserException, ConferenceException {
        return ResponseEntity.ok(conferenceService.checkAvailability(id));
    }
}