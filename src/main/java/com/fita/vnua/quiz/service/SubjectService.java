package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.model.dto.SubjectDto;
import com.fita.vnua.quiz.model.dto.response.Response;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> getAllSubject();

    SubjectDto getSubjectById(Long subjectId);

    SubjectDto create(SubjectDto subjectDto);

    SubjectDto update(Long subjectId, SubjectDto subjectDto);

    Response delete(Long subjectId);
}
