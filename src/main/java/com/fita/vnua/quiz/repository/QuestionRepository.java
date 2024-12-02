package com.fita.vnua.quiz.repository;

import com.fita.vnua.quiz.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.chapter.id = :chapterId")
    List<Question> findByChapter(Long chapterId);
}
