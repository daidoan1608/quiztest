package com.fita.vnua.quiz.controller;

import com.fita.vnua.quiz.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/api/statistics")
    public Map<String, Object> getStatistics() {
        return statisticsService.getStatistics();
    }
}
