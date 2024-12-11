package com.fita.vnua.quiz.dto.request;

import com.fita.vnua.quiz.dto.ExamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private ExamDto examDto;
    private int numberOfQuestion;
}