package com.fita.vnua.quiz.controller;

import com.fita.vnua.quiz.model.dto.UserExamDto;
import com.fita.vnua.quiz.model.dto.request.UserExamRequest;
import com.fita.vnua.quiz.model.dto.response.Response;
import com.fita.vnua.quiz.model.dto.response.UserExamResponse;
import com.fita.vnua.quiz.service.Impl.UserExamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserExamController {
    private final UserExamServiceImpl userExamService;

    @GetMapping("userexams/{userExamId}")
    public ResponseEntity<?> getUserExamById(@PathVariable("userExamId") Long userExamId) {
        UserExamResponse userExam = userExamService.getUserExamById(userExamId);
        if (userExam == null) {
            return ResponseEntity.ok(Response.builder().responseCode("404").responseMessage("No user exam found").build());
        }
        return ResponseEntity.ok(userExam);
    }

    @PostMapping("userexams")
    public ResponseEntity<?> createUserExam(@RequestBody UserExamRequest userExamRequest) {
        UserExamDto saveUserExam = userExamService.createUserExam(userExamRequest);
        if (saveUserExam == null) {
            return ResponseEntity.ok(Response.builder().responseCode("400").responseMessage("User exam not created").build());
        }
        return ResponseEntity.ok(saveUserExam);
    }
}
