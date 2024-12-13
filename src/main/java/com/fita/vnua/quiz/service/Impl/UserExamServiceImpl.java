package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.model.dto.UserAnswerDto;
import com.fita.vnua.quiz.model.dto.UserExamDto;
import com.fita.vnua.quiz.model.dto.request.UserExamRequest;
import com.fita.vnua.quiz.model.entity.*;
import com.fita.vnua.quiz.repository.*;
import com.fita.vnua.quiz.service.UserExamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExamServiceImpl implements UserExamService {
    private final UserExamRepository userExamRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserExamDto getUserExamById(Long id) {
        UserExam userExam = userExamRepository.findById(id).orElse(null);
        return modelMapper.map(userExam, UserExamDto.class);
    }

    @Override
    public UserExamDto createUserExam(UserExamRequest userExamRequest) {
        UserExamDto userExamDto = userExamRequest.getUserExamDto();
        List<UserAnswerDto> userAnswerDtos = userExamRequest.getUserAnswerDtos();

        User user = userRepository.findById(userExamDto.getUserId()).orElse(null);
        Exam exam = examRepository.findById(userExamDto.getExamId()).orElse(null);

        UserExam userExam = new UserExam();
        userExam.setStartTime(userExamDto.getStartTime());
        userExam.setEndTime(userExamDto.getEndTime());
        userExam.setScore(userExamDto.getScore());
        userExam.setUser(user);
        userExam.setExam(exam);

        UserExam savedUserExam = userExamRepository.save(modelMapper.map(userExamDto, UserExam.class));

        for (UserAnswerDto userAnswerDto : userAnswerDtos) {
            // Kiểm tra các trường quan trọng trước khi thao tác
            if (userAnswerDto.getAnswerId() == null ||
                    userAnswerDto.getQuestionId() == null ||
                    savedUserExam == null) {
                // Xử lý trường hợp thiếu dữ liệu
                log.error("Invalid user answer data: {}", userAnswerDto);
                continue;
            }

            userAnswerDto.setUserExamId(savedUserExam.getUserExamId());

            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUserExam(savedUserExam);

            Answer answer = answerRepository.findById(userAnswerDto.getAnswerId())
                    .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + userAnswerDto.getAnswerId()));
            userAnswer.setAnswer(answer);

            Question question = questionRepository.findById(userAnswerDto.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + userAnswerDto.getQuestionId()));
            userAnswer.setQuestion(question);

            userAnswerRepository.save(userAnswer);
        }
        return modelMapper.map(savedUserExam, UserExamDto.class);
    }
}
