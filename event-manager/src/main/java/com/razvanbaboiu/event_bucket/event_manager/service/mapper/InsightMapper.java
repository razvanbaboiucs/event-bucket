package com.razvanbaboiu.event_bucket.event_manager.service.mapper;

import com.razvanbaboiu.event_bucket.event_api.InsightDto;
import com.razvanbaboiu.event_bucket.event_manager.model.Insight;

public class InsightMapper {
    public static Insight map(InsightDto dto) {
        Insight insight = new Insight();
        insight.setTypeId(dto.id());
        insight.setProjectId(dto.projectId());
        insight.setValue(dto.value());
        insight.setTitle(dto.title());
        return insight;
    }

    public static InsightDto map(Insight entity) {
        return new InsightDto(entity.getProjectId(), entity.getTypeId(), entity.getTitle(), entity.getValue());
    }

}
