package com.example.conference.service.impl;

import com.example.conference.dto.CreateUserRequestDto;
import com.example.conference.dto.UserDto;
import com.example.conference.exception.RoleException;
import com.example.conference.exception.UserException;
import com.example.conference.model.Role;
import com.example.conference.model.User;
import com.example.conference.repo.RoleRepository;
import com.example.conference.repo.UserRepository;
import com.example.conference.service.UserService;
import com.example.conference.utils.ObjectTransformerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.conference.constants.Errors.ROLE_NOT_FOUND;
import static com.example.conference.constants.Errors.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void create(CreateUserRequestDto user) throws RoleException {
        log.info("Creating user with params : {} , {} , {} , {} , {}",
                user.getEmail(), user.getRole(), user.getFirstName(),
                user.getSecondName(), user.getLastName());
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setRole(getRoleByName(user.getRole()));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getRole());
        newUser.setSecondName(user.getRole());
        newUser.setLastName(user.getRole());
        userRepository.save(newUser);
        log.info("Creating user finished");
    }

    @Override
    public UserDto getUserById(Long id) throws UserException {
        log.info("Getting user by id: {}", id);
        UserDto result = ObjectTransformerUtils.toUserDto(getExistingUser(id));
        log.info("Getting user finished");
        return result;
    }

    @Override
    public void update(CreateUserRequestDto createUserRequestDto) throws UserException, RoleException {
        log.info("Updating user with id: {}", createUserRequestDto.getId());
        User existingUser = getExistingUser(createUserRequestDto.getId());
        existingUser.setEmail(createUserRequestDto.getEmail());
        existingUser.setRole(getRoleByName(createUserRequestDto.getRole()));
        existingUser.setFirstName(createUserRequestDto.getFirstName());
        existingUser.setSecondName(createUserRequestDto.getSecondName());
        existingUser.setLastName(createUserRequestDto.getLastName());
        userRepository.save(existingUser);
        log.info("Updating user finished");
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {} ", id);
        userRepository.deleteById(id);
        log.info("Deleting user finished");
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Getting all users");
        List<UserDto> result = ObjectTransformerUtils.toUserDtoList(userRepository.findAll());
        log.info("Getting all users finished");
        return result;
    }

    private User getExistingUser(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    private Role getRoleByName(String name) throws RoleException {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleException(ROLE_NOT_FOUND));
    }
}
