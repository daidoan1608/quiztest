package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.QuestionDto;
import com.fita.vnua.quiz.model.entity.Question;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAllQuestion();

    void create(QuestionDto questionDto);

    void update(Long questionId, QuestionDto questionDto);

    void delete(Long questionId);

}
