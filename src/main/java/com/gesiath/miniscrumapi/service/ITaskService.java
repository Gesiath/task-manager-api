package com.gesiath.miniscrumapi.service;

import com.gesiath.miniscrumapi.dto.CreateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.TaskResponseDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskStatusRequestDTO;
import com.gesiath.miniscrumapi.enumerator.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ITaskService {

    Page<TaskResponseDTO> getAll(Pageable pageable);
    TaskResponseDTO getById(String id);
    Page<TaskResponseDTO> getByStatus(Status status, Pageable pageable);
    Page<TaskResponseDTO> getByUser_Id(String user_Id, Pageable pageable);
    Page<TaskResponseDTO> getByUser_IdAndStatus(String user_Id, Status status, Pageable pageable);
    TaskResponseDTO create(CreateTaskRequestDTO dto);
    TaskResponseDTO update(String id, UpdateTaskRequestDTO dto);
    TaskResponseDTO patchStatus(String id, UpdateTaskStatusRequestDTO dto);
    void delete(String id);
}
