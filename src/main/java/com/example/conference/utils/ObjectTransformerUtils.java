package com.example.conference.utils;

import com.example.conference.dto.ConferenceDto;
import com.example.conference.dto.UserDto;
import com.example.conference.model.Conference;
import com.example.conference.model.User;

import java.util.ArrayList;
import java.util.List;

public class ObjectTransformerUtils {


    public static List<ConferenceDto> toConferenceDtoList(List<Conference> conferenceList) {
        List<ConferenceDto> result = new ArrayList<>();
        for (Conference conference : conferenceList) {
            result.add(toConferenceDto(conference));
        }
        return result;
    }

    public static ConferenceDto toConferenceDto(Conference conference) {
        ConferenceDto result = new ConferenceDto();
        result.setId(conference.getId());
        result.setName(conference.getName());
        result.setBeginTime(conference.getBeginTime());
        result.setEndTime(conference.getEndTime());
        result.setIsCancelled(conference.getIsCancelled());
        result.setMaxSeats(conference.getMaxSeats());
        result.setParticipants(toUserDtoList(conference.getParticipants().stream().toList()));
        return result;
    }

    public static List<UserDto> toUserDtoList(List<User> userList) {
        List<UserDto> result = new ArrayList<>();
        for (User conference : userList) {
            result.add(toUserDto(conference));
        }
        return result;
    }

    public static UserDto toUserDto(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setRole(user.getRole().getName());
        result.setFirstName(user.getFirstName());
        result.setSecondName(user.getSecondName());
        result.setLastName(user.getLastName());
        return result;
    }
}
