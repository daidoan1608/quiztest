package com.fita.vnua.quiz.service;

import com.fita.vnua.quiz.dto.ChapterDto;
import com.fita.vnua.quiz.dto.response.Response;

import java.util.List;

public interface ChapterService {
    List<ChapterDto> getChapterBySubject(Long subjectId);
    List<ChapterDto> getAllChapter();

    Response create(ChapterDto chapterDto);

    Response update(Long chapterId, ChapterDto chapterDto);

    Response delete(Long chapterId);
}

