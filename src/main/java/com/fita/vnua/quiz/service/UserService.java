package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.UserDto;
import com.fita.vnua.quiz.dto.response.Response;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID userId);
    List<UserDto> getUserBySearchKey(String keyword);
    Response create(UserDto userDto);
    Response update(UUID userId, UserDto userDto);
    Response delete(UUID userId);
}
