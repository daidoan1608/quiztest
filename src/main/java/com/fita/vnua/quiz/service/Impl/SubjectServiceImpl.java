package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.dto.SubjectDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.model.entity.Subject;
import com.fita.vnua.quiz.repository.SubjectRepository;
import com.fita.vnua.quiz.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<SubjectDto> getAllSubject() {
        return subjectRepository.findAll().stream().map(subject -> modelMapper.map(subject, SubjectDto.class)).toList();
    }

    @Override
    public Response create(SubjectDto subjectDto) {
        subjectRepository.save(modelMapper.map(subjectDto, Subject.class));
        return Response.builder()
                .responseMessage("Subject created successfully")
                .responseCode("200 OK").build();
    }

    @Override
    public Response update(Long subjectId, SubjectDto subjectDto) {
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
        String oldName = subjectDto.getName();
        String newName = existingSubject.getName();
        existingSubject.setName(subjectDto.getName());
        existingSubject.setDescription(subjectDto.getDescription());
        subjectRepository.save(existingSubject);
        return Response.builder()
                .responseMessage("Subject updated successfully : " + oldName + " -> " + newName)
                .responseCode("200 OK").build();
    }

    @Override
    public Response delete(Long subjectId) {
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
        subjectRepository.delete(existingSubject);
        return Response.builder()
                .responseMessage("Subject deleted successfully")
                .responseCode("200 OK").build();
    }
}
