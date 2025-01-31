package com.razvanbaboiu.event_bucket.event_manager.service.mapper;

import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
import com.razvanbaboiu.event_bucket.event_manager.model.Identification;

public class IdentificationMapper {
    public static Identification map(IdentificationDto dto) {
        Identification identification = new Identification();
        identification.setProjectId(dto.projectId());
        identification.setUserId(dto.userId());
        return identification;
    }

    public static IdentificationDto map(Identification entity) {
        return new IdentificationDto(null,
                entity.getProjectId(),
                entity.getUserId());
    }
}
