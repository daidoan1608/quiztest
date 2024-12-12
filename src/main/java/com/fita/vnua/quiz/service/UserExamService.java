package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.model.dto.UserExamDto;
import com.fita.vnua.quiz.model.dto.request.UserExamRequest;

public interface UserExamService {
    UserExamDto getUserExamById(Long id);

    UserExamDto createUserExam(UserExamRequest userExamRequest);


}
