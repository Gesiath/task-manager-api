package com.gesiath.miniscrumapi.mapper;

import com.gesiath.miniscrumapi.dto.SprintResponseDTO;
import com.gesiath.miniscrumapi.entity.Sprint;

public class SprintMapper {

    public static SprintResponseDTO toResponse(Sprint sprint){

        return SprintResponseDTO.builder()
                .id(sprint.getId())
                .title(sprint.getTitle())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .status(sprint.getStatus())
                .build();
    }

}
