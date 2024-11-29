package com.fita.vnua.quiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExamDto {
    private Long examId;
    private String title;
    private String description;
    private Integer duration;
    private UUID createdBy;
}
