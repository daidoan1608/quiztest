package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.SubjectDto;
import com.fita.vnua.quiz.dto.response.Response;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> getAllSubject();

    Response create(SubjectDto subjectDto);

    Response update(Long subjectId, SubjectDto subjectDto);

    Response delete(Long subjectId);
}
