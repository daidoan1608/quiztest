package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.model.dto.QuestionDto;
import com.fita.vnua.quiz.model.dto.response.Response;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByChapterId(Long chapterId);

    List<QuestionDto> getAllQuestion();

    List<QuestionDto> getQuestionsBySubject(Long subjectId);

    List<QuestionDto> getQuestionsBySubjectAndNumber(Long subjectId, int number);

    List<QuestionDto> getQuestionsByExamId(Long examId);

    QuestionDto create(QuestionDto questionDto);

    QuestionDto update(Long questionId, QuestionDto questionDto);

    Response delete(Long questionId);

}
