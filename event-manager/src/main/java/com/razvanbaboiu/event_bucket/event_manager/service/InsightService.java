package com.razvanbaboiu.event_bucket.event_manager.service;

import com.razvanbaboiu.event_bucket.event_api.InsightDto;
import com.razvanbaboiu.event_bucket.event_api.InsightMutationDto;
import com.razvanbaboiu.event_bucket.event_api.MutationType;
import com.razvanbaboiu.event_bucket.event_manager.model.Insight;
import com.razvanbaboiu.event_bucket.event_manager.repository.InsightRepository;
import com.razvanbaboiu.event_bucket.event_manager.service.mapper.InsightMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;

    @Transactional
    public void mutateInsight(InsightMutationDto mutationDto) {
        Optional<Insight> insight = insightRepository.findByTypeIdAndProjectId(mutationDto.id(), mutationDto.projectId());
        insight.ifPresentOrElse((i) -> {
            Insight mutatedInsight = doMutation(i, mutationDto);
            insightRepository.save(mutatedInsight);
        }, () -> {
            throw new NoSuchElementException("No insight with the given id found");
        });
    }

    private Insight doMutation(Insight insight, InsightMutationDto mutationDto) {
        BigDecimal mutationValue = mutationDto.mutationType().equals(MutationType.INCREMENT)
                ? mutationDto.mutationValue()
                : mutationDto.mutationValue().negate();
        insight.setValue(insight.getValue().add(mutationValue));
        return insight;
    }

    @Transactional
    public void addInsight(InsightDto dto) {
        insightRepository.findByTypeIdAndProjectId(dto.id(), dto.projectId())
                .ifPresent(_ -> {
                    throw new RuntimeException("Insight with given id already exists");
                });
        insightRepository.save(InsightMapper.map(dto));
    }

    public List<InsightDto> getAllInsights(String projectId) {
        return insightRepository.findAllByProjectId(projectId).stream()
                .map(InsightMapper::map)
                .toList();
    }
}
