package com.fita.vnua.quiz.controller;

import com.fita.vnua.quiz.dto.ExamDto;
import com.fita.vnua.quiz.dto.request.ExamRequest;
import com.fita.vnua.quiz.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PostMapping("admin/exams")
    public ResponseEntity<ExamDto> createExam(@RequestBody ExamRequest examRequest) {
        return ResponseEntity.ok(examService.createExam(examRequest.getExamDto(),examRequest.getNumberOfQuestion()));
    }

    @GetMapping("admin/exams")
    public ResponseEntity<?> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("admin/exams/{examId}")
    public ResponseEntity<?> getExamById(@PathVariable("examId") Long examId) {
        return ResponseEntity.ok(examService.getExamById(examId));
    }
}
