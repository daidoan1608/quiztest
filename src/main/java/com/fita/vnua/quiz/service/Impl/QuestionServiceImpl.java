package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.dto.AnswerDto;
import com.fita.vnua.quiz.dto.QuestionDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.model.entity.Answer;
import com.fita.vnua.quiz.model.entity.Chapter;
import com.fita.vnua.quiz.model.entity.Question;
import com.fita.vnua.quiz.repository.AnswerRepository;
import com.fita.vnua.quiz.repository.ChapterRepository;
import com.fita.vnua.quiz.repository.QuestionRepository;
import com.fita.vnua.quiz.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<QuestionDto> getQuestionsByChapterId(Long chapterId) {
        return questionRepository.findByChapter(chapterId).stream().map(question -> modelMapper.map(question, QuestionDto.class)).toList();
    }

    @Override
    public List<QuestionDto> getAllQuestion() {
        return questionRepository.findAll().stream().map(question -> modelMapper.map(question, QuestionDto.class)).toList();
    }

    @Override
    public List<QuestionDto> getQuestionsBySubject(Long subjectId) {
        return questionRepository.findQuestionsBySubjectId(subjectId).stream().map(question -> modelMapper.map(question, QuestionDto.class)).toList();
    }

    @Override
    public Response create(QuestionDto questionDto) {
        // Tạo đối tượng Question mới
        System.out.println("Received QuestionDto: " + questionDto.toString());


        Question question = new Question();
        question.setContent(questionDto.getContent());
        question.setDifficulty(Question.Difficulty.valueOf(questionDto.getDifficulty())); // Giả sử bạn có getter cho độ khó

        // Gán chapter cho question
        Chapter chapter = chapterRepository.findById(questionDto.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        question.setChapter(chapter);

        // Kiểm tra danh sách câu trả lời
        if (questionDto.getAnswers() != null && !questionDto.getAnswers().isEmpty()) {
            for (AnswerDto answerDto : questionDto.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerDto.getContent());
                answer.setIsCorrect(answerDto.getIsCorrect());

                // Gán Question cho Answer
                answer.setQuestion(question);
            }
        }

        // Lưu đối tượng Question (các Answer sẽ tự động được lưu do CascadeType.ALL)
        question = questionRepository.save(question); // ID sẽ được gán tại đây

        // Lưu các Answer sau khi Question đã có ID
        if (questionDto.getAnswers() != null && !questionDto.getAnswers().isEmpty()) {
            for (AnswerDto answerDto : questionDto.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerDto.getContent());
                answer.setIsCorrect(answerDto.getIsCorrect());

                // Gán Question cho Answer
                answer.setQuestion(question);

                // Lưu Answer vào database
                answerRepository.save(answer);
            }
        }


        // Trả về phản hồi
        return Response.builder()
                .responseMessage("Question created successfully")
                .responseCode("200 OK").build();
    }

    @Override
    public Response update(Long questionId, QuestionDto questionDto) {
        // Tìm câu hỏi hiện tại
        var existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        // Cập nhật thông tin của câu hỏi
        String oldContent = existingQuestion.getContent();
        String newContent = questionDto.getContent();
        existingQuestion.setContent(newContent);

        // Cập nhật danh sách câu trả lời
        if (questionDto.getAnswers() != null) {
            // Xóa các câu trả lời cũ
            answerRepository.deleteAll(existingQuestion.getAnswers());

            // Lưu các câu trả lời mới
            for (AnswerDto answerDto : questionDto.getAnswers()) {
                Answer answer = modelMapper.map(answerDto, Answer.class);
                answer.setQuestion(existingQuestion); // Gắn câu hỏi vào câu trả lời
                answerRepository.save(answer);
            }
        }

        // Lưu câu hỏi đã cập nhật
        questionRepository.save(existingQuestion);

        // Trả về phản hồi
        return Response.builder()
                .responseMessage("Question and answers updated successfully: " + oldContent + " -> " + newContent)
                .responseCode("200 OK").build();
    }

    @Override
    public Response delete(Long questionId) {
        // Tìm kiếm câu hỏi theo ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        // Xóa câu hỏi (cùng với tất cả câu trả lời nhờ cascade)
        questionRepository.delete(question);

        // Trả về phản hồi
        return Response.builder()
                .responseMessage("Question deleted successfully")
                .responseCode("200 OK").build();
    }
}
