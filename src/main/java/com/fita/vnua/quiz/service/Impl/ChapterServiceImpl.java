package com.fita.vnua.quiz.service.Impl;

import com.fita.vnua.quiz.dto.ChapterDto;
import com.fita.vnua.quiz.dto.response.Response;
import com.fita.vnua.quiz.model.entity.Chapter;
import com.fita.vnua.quiz.repository.ChapterRepository;
import com.fita.vnua.quiz.service.ChapterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ChapterDto> getChapterBySubject(Long subjectId) {
        return chapterRepository.findBySubject(subjectId).stream().map(chapter -> modelMapper.map(chapter, ChapterDto.class)).toList();
    }

    @Override
    public List<ChapterDto> getAllChapter() {
        return chapterRepository.findAll().stream().map(chapter -> modelMapper.map(chapter, ChapterDto.class)).toList();
    }

    @Override
    public Response create(ChapterDto chapterDto) {
        chapterRepository.save(modelMapper.map(chapterDto, Chapter.class));
        return Response.builder()
                .responseMessage("Chapter created successfully")
                .responseCode("200 OK").build();
    }

    @Override
    public Response update(Long chapterId, ChapterDto chapterDto) {
        var existingChapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        String oldName = chapterDto.getName();
        String newName = existingChapter.getName();
        existingChapter.setName(chapterDto.getName());
        existingChapter.setChapterNumber(chapterDto.getChapterNumber());
        chapterRepository.save(existingChapter);
        return Response.builder()
                .responseMessage("Chapter updated successfully : " + oldName + " -> " + newName)
                .responseCode("200 OK").build();
    }

    @Override
    public Response delete(Long chapterId) {
        var existingChapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        chapterRepository.delete(existingChapter);
        return Response.builder()
                .responseMessage("Chapter deleted successfully")
                .responseCode("200 OK").build();
    }
}
