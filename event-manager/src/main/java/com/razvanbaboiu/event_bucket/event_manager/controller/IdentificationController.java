package com.razvanbaboiu.event_bucket.event_manager.controller;

import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
import com.razvanbaboiu.event_bucket.event_manager.service.IdentificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identifications")
@Slf4j
@RequiredArgsConstructor
public class IdentificationController {
    private final IdentificationService identificationService;

    @RequestMapping("/projects/{projectId}")
    public List<IdentificationDto> getIdentifications(@PathVariable String projectId) {
        log.info("Get all identifications for project: {}", projectId);
        return identificationService.getAllIdentifications(projectId);
    }
}
