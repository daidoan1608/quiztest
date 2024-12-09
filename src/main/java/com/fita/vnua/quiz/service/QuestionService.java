package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.QuestionDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.model.entity.Question;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getQuestionsByChapterId(Long chapterId);
    List<QuestionDto> getAllQuestion();

    List<QuestionDto> getQuestionsBySubject(Long subjectId);

    Response create(QuestionDto questionDto);

    Response update(Long questionId, QuestionDto questionDto);

    Response delete(Long questionId);

}
