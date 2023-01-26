package com.example.conference.service.impl;

import com.example.conference.dto.ConferenceDto;
import com.example.conference.dto.response.ConferenceStatusResponseDto;
import com.example.conference.enums.ConferenceStatus;
import com.example.conference.exception.ConferenceException;
import com.example.conference.exception.UserException;
import com.example.conference.model.Conference;
import com.example.conference.model.User;
import com.example.conference.repo.ConferenceRepository;
import com.example.conference.repo.UserRepository;
import com.example.conference.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.conference.constants.Errors.CONFERENCE_NOT_FOUND;
import static com.example.conference.constants.Errors.USER_NOT_FOUND;
import static com.example.conference.enums.ConferenceStatus.*;
import static com.example.conference.utils.ObjectTransformerUtils.toConferenceDto;
import static com.example.conference.utils.ObjectTransformerUtils.toConferenceDtoList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;

    private final UserRepository userRepository;

    @Override
    public void create(ConferenceDto conference, User user) {
        log.info("Creating conference with name: {} , beginTime: {} , endTime: {}",
                conference.getName(), conference.getBeginTime(), conference.getEndTime());
        Conference newConf = new Conference();
        newConf.setName(conference.getName());
        newConf.setBeginTime(conference.getBeginTime());
        newConf.setEndTime(conference.getBeginTime());
        newConf.setMaxSeats(conference.getMaxSeats());
        newConf.setOwner(user);
        conferenceRepository.save(newConf);
        log.info("Creating conference finished");
    }

    public ConferenceDto getById(Long id) throws ConferenceException {
        log.info("Getting conference with id: {}", id);
        Conference conference = getExistingConf(id);
        ConferenceDto result = toConferenceDto(conference);
        log.info("Getting conference with finished");
        return result;
    }

    @Override
    public void update(ConferenceDto conference) throws ConferenceException {
        log.info("Updating conference with id: {}", conference.getId());
        Conference existingConf = getExistingConf(conference.getId());
        existingConf.setName(conference.getName());
        existingConf.setBeginTime(conference.getBeginTime());
        existingConf.setEndTime(conference.getEndTime());
        conferenceRepository.save(existingConf);
        log.info("Updating conference finished");
    }

    @Override
    public void cancel(Long id) throws ConferenceException {
        log.info("Cancelling conference with id:{}", id);
        cancelConference(id);
        log.info("Cancelling conference finished");
    }

    private void cancelConference(Long id) throws ConferenceException {
        Conference conf = getExistingConf(id);
        conf.setIsCancelled(true);
        conferenceRepository.save(conf);

    }

    @Override
    public void delete(Long id) {
        log.info("Deleting conference with id:{}", id);
        conferenceRepository.deleteById(id);
        log.info("Deleting conference finished");
    }

    @Override
    public List<ConferenceDto> getAll(User user) {
        log.info("Getting all conferences of current user");
        List<ConferenceDto> result = toConferenceDtoList(conferenceRepository.findByOwnerId(user.getId()));
        log.info("Getting all conferences finished");
        return result;
    }

    @Override
    public void addParticipant(Long id, String email) throws ConferenceException, UserException {
        log.info("Adding new participant with email: {} into conference with id: {}", email, id);
        Conference existingConf = getExistingConf(id);
        addNewParticipant(existingConf, getUserByEmail(email));
        conferenceRepository.save(existingConf);
        log.info("Adding new participant to conference finished");
    }

    @Override
    public void removeParticipant(Long id, String email) throws ConferenceException, UserException {
        log.info("Removing participant with email: {} from conference with id: {}", email, id);
        Conference existingConf = getExistingConf(id);
        removeParticipant(existingConf, getUserByEmail(email));
        conferenceRepository.save(existingConf);
        log.info("Adding new participant to conference finished");
    }

    @Override
    public ConferenceStatusResponseDto checkAvailability(Long id) throws ConferenceException {
        log.info("Getting status of conference with id: {}", id);
        ConferenceStatusResponseDto result = new ConferenceStatusResponseDto(getConferenceStatus(id).getValue());
        log.info("Getting status finished");
        return result;
    }

    private ConferenceStatus getConferenceStatus(Long id) throws ConferenceException {
        Conference conference = getExistingConf(id);
        if (conference.getIsCancelled()) {
            return CANCELLED;
        } else if (conference.getMaxSeats() == conference.getParticipants().size()) {
            return BUSY;
        } else {
            return AVAILABLE;
        }
    }

    private Conference getExistingConf(Long id) throws ConferenceException {
        return conferenceRepository
                .findById(id)
                .orElseThrow(() -> new ConferenceException(CONFERENCE_NOT_FOUND));
    }

    private void addNewParticipant(Conference conf, User user) {
        Set<User> participants = conf.getParticipants();
        participants.add(user);
        conf.setParticipants(participants);
    }

    private void removeParticipant(Conference conf, User user) {
        Set<User> participants = conf.getParticipants();
        participants.remove(user);
        conf.setParticipants(participants);
    }

    private User getUserByEmail(String email) throws UserException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}