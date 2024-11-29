package com.fita.vnua.quiz.model.entity;

import com.fita.vnua.quiz.genaretor.ExamSubjectId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExamSubject {
    @EmbeddedId
    private ExamSubjectId id;

    @ManyToOne
    @MapsId("examId") // Ánh xạ khóa ngoại "examId" trong ExamSubjectId
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne
    @MapsId("subjectId") // Ánh xạ khóa ngoại "subjectId" trong ExamSubjectId
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}
