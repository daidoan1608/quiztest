package com.fita.vnua.quiz.dto;

import com.fita.vnua.quiz.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserDto {
    private UUID userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private User.Role role;
}
