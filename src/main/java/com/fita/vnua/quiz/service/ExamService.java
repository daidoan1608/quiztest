package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.ExamDto;
import com.fita.vnua.quiz.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface ExamService {
    List<ExamDto> getAllExams();

    ExamDto getExamById(Long id);

    ExamDto createExam(ExamDto examDto, int numberOfQuestions);

    ExamDto updateExam(Long id, ExamDto examDto);

    void deleteExam(Long id);
}
