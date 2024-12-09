package com.fita.vnua.quiz.repository;

import com.fita.vnua.quiz.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.chapter.id = :chapterId")
    List<Question> findByChapter(@Param("chapterId") Long chapterId);

    @Query("SELECT q FROM Question q JOIN q.chapter c JOIN c.subject s WHERE s.subjectId = :subjectId")
    List<Question> findQuestionsBySubjectId(@Param("subjectId") Long subjectId);
}
