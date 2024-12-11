package com.fita.vnua.quiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExamDto {
    private Long examId;
    private Long subjectId;
    private String title;
    private String description;
    private Integer duration;
    private UUID createdBy;
    private String createdDate;
    private List<QuestionDto> questions;
}
