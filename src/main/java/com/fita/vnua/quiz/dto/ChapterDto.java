package com.fita.vnua.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChapterDto {
    private Long chapterId;
    private String name;
    private Integer ChapterNumber;
    private Long subjectId;
}
