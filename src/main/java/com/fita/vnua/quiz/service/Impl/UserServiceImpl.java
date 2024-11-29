package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.dto.UserDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.model.entity.User;
import com.fita.vnua.quiz.repository.UserRepository;
import com.fita.vnua.quiz.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(UUID userId) {
        return userRepository.findById(userId).map(user -> modelMapper.map(user, UserDto.class)).orElse(null);
    }

    @Override
    public List<UserDto> getUserBySearchKey(String keyword) {
        log.info("Searching for users with keyword: {}", keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return userRepository.findByUsernameContainingOrFullNameContaining(keyword)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public Response create(UserDto userDto) {
        userRepository.save(modelMapper.map(userDto, User.class));
        return Response.builder()
                .responseMessage("User created successfully")
                .responseCode("200 OK").build();
    }


    @Override
    public Response update(UUID userId, UserDto userDto) {
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setFullName(userDto.getFullName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRole(userDto.getRole());
        var updatedUser = userRepository.save(existingUser);
        return Response.builder()
                .responseMessage("User updated successfully")
                .responseCode("200 OK").build();
    }

    @Override
    public Response delete(UUID userId) {
        userRepository.deleteById(userId);
        return Response.builder()
                .responseMessage("User deleted successfully")
                .responseCode("200 OK").build();
    }
}
