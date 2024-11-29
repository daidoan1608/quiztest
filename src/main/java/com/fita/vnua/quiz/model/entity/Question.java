package com.fita.vnua.quiz.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty; // Enum: EASY, MEDIUM, HARD

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}

