package com.fita.vnua.quiz.repository;

import com.fita.vnua.quiz.model.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Exam findExamByExamId(Long examId);
}
