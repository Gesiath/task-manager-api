package com.gesiath.miniscrumapi.service;

import com.gesiath.miniscrumapi.dto.CreateSprintRequestDTO;
import com.gesiath.miniscrumapi.dto.SprintResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ISprintService {

    SprintResponseDTO create(CreateSprintRequestDTO dto);
    Page<SprintResponseDTO> getAll(Pageable pageable);
    SprintResponseDTO startSprint(String id);
    SprintResponseDTO closeSprint(String id);
    void assignTask(String sprintId, String taskId);

}
