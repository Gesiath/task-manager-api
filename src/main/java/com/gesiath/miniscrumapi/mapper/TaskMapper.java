package com.gesiath.miniscrumapi.mapper;

import com.gesiath.miniscrumapi.dto.TaskResponseDTO;
import com.gesiath.miniscrumapi.entity.Task;

public class TaskMapper {

    public static TaskResponseDTO toResponse(Task task){

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .doDate(task.getDoDate())
                .createdAt(task.getCreatedAt())
                .userName(task.getUser() != null ? task.getUser().getName() : null)
                .sprintId(task.getSprint() != null ? task.getSprint().getId() : null)
                .sprintName(task.getSprint() != null ? task.getSprint().getTitle() : null)
                .build();
    }
}
