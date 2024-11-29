package com.fita.vnua.quiz.genaretor;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@NoArgsConstructor
@Embeddable
public class ExamSubjectId implements Serializable {
    private Long examId;
    private Long subjectId;

    // Override equals() và hashCode() để đảm bảo hoạt động của composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamSubjectId that = (ExamSubjectId) o;
        return Objects.equals(examId, that.examId) &&
                Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, subjectId);
    }
}

