package com.razvanbaboiu.event_bucket.event_manager.controller;

import com.razvanbaboiu.event_bucket.event_api.InsightDto;
import com.razvanbaboiu.event_bucket.event_manager.service.InsightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insights")
@Slf4j
@RequiredArgsConstructor
public class InsightController {
    private final InsightService insightService;

    @PostMapping
    public void createInsight(@RequestBody InsightDto dto) {
        log.info("Create new insight: {}", dto);
        insightService.addInsight(dto);
    }

    @GetMapping("/{projectId}")
    public List<InsightDto> getInsights(@PathVariable String projectId) {
        log.info("Get insights");
        return insightService.getAllInsights(projectId);
    }
}
