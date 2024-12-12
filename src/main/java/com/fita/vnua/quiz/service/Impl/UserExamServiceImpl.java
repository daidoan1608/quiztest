package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.model.dto.UserAnswerDto;
import com.fita.vnua.quiz.model.dto.UserExamDto;
import com.fita.vnua.quiz.model.dto.request.UserExamRequest;
import com.fita.vnua.quiz.model.entity.*;
import com.fita.vnua.quiz.repository.*;
import com.fita.vnua.quiz.service.UserExamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
            userAnswerDto.setUserExamId(savedUserExam.getUserExamId());
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUserExam(savedUserExam);

            Answer answer = answerRepository.findById(userAnswerDto.getAnswerId()).orElse(null);
            userAnswer.setAnswer(answer);

            Question question = questionRepository.findById(userAnswerDto.getQuestionId()).orElse(null);
            userAnswer.setQuestion(question);
            userAnswerRepository.save(userAnswer);
        }
        return modelMapper.map(savedUserExam, UserExamDto.class);
    }
}
