package com.example.conference.service;

import com.example.conference.dto.ConferenceDto;
import com.example.conference.dto.response.ConferenceStatusResponseDto;
import com.example.conference.exception.ConferenceException;
import com.example.conference.exception.UserException;
import com.example.conference.model.User;

import java.util.List;

public interface ConferenceService {

    void create(ConferenceDto conference,User user);

    ConferenceDto getById(Long id) throws UserException, ConferenceException;

    void update(ConferenceDto conference) throws ConferenceException;

    void delete(Long id);

    List<ConferenceDto> getAll(User user);

    void cancel(Long id) throws ConferenceException;

    void addParticipant(Long id, String email) throws ConferenceException, UserException;

    void removeParticipant(Long id, String email) throws ConferenceException, UserException;

    ConferenceStatusResponseDto checkAvailability(Long id) throws ConferenceException;
}