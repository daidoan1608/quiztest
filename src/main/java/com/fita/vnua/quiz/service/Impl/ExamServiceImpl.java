package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.dto.ExamDto;
import com.fita.vnua.quiz.dto.QuestionDto;
import com.fita.vnua.quiz.genaretor.ExamQuestionId;
import com.fita.vnua.quiz.model.entity.*;
import com.fita.vnua.quiz.repository.*;
import com.fita.vnua.quiz.service.ExamService;
import com.fita.vnua.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final ExamQuestionRepository examQuestionRepository;
    private final UserRepository userRepository;

    @Override
    public List<ExamDto> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        List<ExamDto> examDtos = new ArrayList<>();
        for (Exam exam : exams) {
            ExamDto examDto = new ExamDto();
            examDto.setExamId(exam.getExamId());
            examDto.setTitle(exam.getTitle());
            examDto.setDescription(exam.getDescription());
            examDto.setDuration(exam.getDuration());
            examDto.setSubjectId(exam.getSubject().getSubjectId());
            examDto.setCreatedBy(exam.getCreatedBy().getUserId());
            examDto.setCreatedDate(String.valueOf(exam.getCreatedTime()));
            examDto.setQuestions(questionService.getQuestionsByExamId(exam.getExamId()));
            examDtos.add(examDto);
        }
        return examDtos;
    }

    @Override
    public ExamDto getExamById(Long id) {
        Exam exam = examRepository.findExamByExamId(id);
        ExamDto examDto = new ExamDto();
        examDto.setExamId(exam.getExamId());
        examDto.setTitle(exam.getTitle());
        examDto.setDescription(exam.getDescription());
        examDto.setDuration(exam.getDuration());
        examDto.setSubjectId(exam.getSubject().getSubjectId());
        examDto.setCreatedBy(exam.getCreatedBy().getUserId());
        examDto.setCreatedDate(String.valueOf(exam.getCreatedTime()));
        examDto.setQuestions(questionService.getQuestionsByExamId(exam.getExamId()));
        return examDto;
    }

    @Override
    public ExamDto createExam(ExamDto examDto, int numberOfQuestions) {
        // Tạo Exam mới
        Exam exam = new Exam();
        exam.setTitle(examDto.getTitle());
        exam.setSubject(subjectRepository.findById(examDto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));
        exam.setDescription(examDto.getDescription());
        exam.setDuration(examDto.getDuration());
        exam.setCreatedBy(userRepository.findById(examDto.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found")));
        exam.setCreatedTime(LocalDate.now());
        exam = examRepository.save(exam);
        examDto.setExamId(exam.getExamId());
        examDto.setCreatedDate(String.valueOf(exam.getCreatedTime()));
        // Lấy câu hỏi ngẫu nhiên
        List<QuestionDto> questionDtos = questionService.getQuestionsBySubjectAndNumber(examDto.getSubjectId(),numberOfQuestions);
        List<Question> randomQuestions = questionDtos.stream().map(questionDto -> modelMapper.map(questionDto, Question.class)).toList();

        examDto.setQuestions(questionDtos);
        // Tạo liên kết ExamQuestion
        for (Question question : randomQuestions) {
            ExamQuestionId examQuestionId = new ExamQuestionId();
            examQuestionId.setExamId(exam.getExamId());
            examQuestionId.setQuestionId(question.getQuestionId());

            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExam(exam);
            examQuestion.setQuestion(question);

            examQuestion.setId(examQuestionId);

            examQuestionRepository.save(examQuestion);

        }
        return examDto;
    }


    @Override
    public ExamDto updateExam(Long id, ExamDto examDto) {
        return null;
    }

    @Override
    public void deleteExam(Long id) {

    }
}