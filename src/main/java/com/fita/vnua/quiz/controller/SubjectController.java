package com.fita.vnua.quiz.controller;

import com.fita.vnua.quiz.dto.SubjectDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.service.Impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    @GetMapping("subjects")
    ResponseEntity<?> getAllSubject() {
        List<SubjectDto> subjects = subjectService.getAllSubject();
        if (subjects.isEmpty()) {
            return ResponseEntity.ok(Response.builder().responseCode("404").responseMessage("No subject found").build());
        }
        return ResponseEntity.ok(subjectService.getAllSubject());
    }

    @PostMapping("admin/subjects")
    ResponseEntity<?> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.create(subjectDto));
    }

    @PatchMapping("admin/subjects/{subjectId}")
    ResponseEntity<?> updateSubject(@PathVariable("subjectId") Long subjectId, @RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.update(subjectId, subjectDto));
    }

    @DeleteMapping("admin/subjects/{subjectId}")
    ResponseEntity<?> deleteSubject(@PathVariable("subjectId") Long subjectId) {
        return ResponseEntity.ok(subjectService.delete(subjectId));
    }
}