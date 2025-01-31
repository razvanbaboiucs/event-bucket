package com.razvanbaboiu.event_bucket.event_manager.service;

import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
import com.razvanbaboiu.event_bucket.event_manager.model.Identification;
import com.razvanbaboiu.event_bucket.event_manager.repository.IdentificationRepository;
import com.razvanbaboiu.event_bucket.event_manager.service.mapper.IdentificationMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdentificationService {
    private final IdentificationRepository identificationRepository;

    public IdentificationService(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

    public Optional<Identification> getIdentificationForUserId(String userId, String projectId) {
        return identificationRepository.findByUserIdAndProjectId(userId, projectId);
    }

    @Transactional
    public void saveIdentification(IdentificationDto identificationDto) {
        identificationRepository.save(IdentificationMapper.map(identificationDto));
    }

    public List<IdentificationDto> getAllIdentifications(String projectId) {
        return identificationRepository.findAllByProjectId(projectId).stream()
                .map(IdentificationMapper::map)
                .toList();
    }
}
