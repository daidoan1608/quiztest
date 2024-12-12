package com.fita.vnua.quiz.repository;

import com.fita.vnua.quiz.model.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
