package com.gesiath.miniscrumapi.service;

import com.gesiath.miniscrumapi.dto.CreateSprintRequestDTO;
import com.gesiath.miniscrumapi.dto.SprintResponseDTO;
import com.gesiath.miniscrumapi.entity.Sprint;
import com.gesiath.miniscrumapi.entity.Task;
import com.gesiath.miniscrumapi.enumerator.SprintStatus;
import com.gesiath.miniscrumapi.enumerator.Status;
import com.gesiath.miniscrumapi.exception.CustomDataNotFoundException;
import com.gesiath.miniscrumapi.mapper.SprintMapper;
import com.gesiath.miniscrumapi.respository.SprintRepository;
import com.gesiath.miniscrumapi.respository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SprintService implements ISprintService{

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;

    public SprintService(SprintRepository sprintRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public SprintResponseDTO create(CreateSprintRequestDTO dto) {

        if (dto.getEndDate().isBefore(dto.getStartDate())) {

            throw new IllegalStateException("End date cannot be before start date");

        }

        Sprint sprint = Sprint.builder()
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(SprintStatus.PLANNED)
                .build();

        return SprintMapper.toResponse(sprintRepository.save(sprint));

    }

    @Override
    public Page<SprintResponseDTO> getAll(Pageable pageable){

        return sprintRepository.findAll(pageable)
                .map(SprintMapper::toResponse);

    }

    @Override
    public SprintResponseDTO startSprint(String id){

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new CustomDataNotFoundException("Sprint  not found"));

        if (sprint.getStatus() != SprintStatus.PLANNED){

            throw new IllegalStateException("Only planned sprint can be started");

        }

        boolean existsActiveSprint = sprintRepository
                .existsByStatus(SprintStatus.ACTIVE);

        if (existsActiveSprint) {
            throw new IllegalStateException("There is already an active sprint");
        }

        sprint.setStatus(SprintStatus.ACTIVE);

        return SprintMapper.toResponse(sprintRepository.save(sprint));

    }

    @Override
    public SprintResponseDTO closeSprint(String id){

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new CustomDataNotFoundException("Sprint not found"));

        if (sprint.getStatus() != SprintStatus.ACTIVE){

            throw new IllegalStateException("Only active sprint can be closed");

        }

        boolean hasPendingTasks = sprint.getTasks()
                .stream()
                .anyMatch(task -> task.getStatus() != Status.DONE);

        if (hasPendingTasks){

            throw new IllegalStateException("Cannot close sprint with unfinished tasks");

        }

        sprint.setStatus(SprintStatus.CLOSED);

        return SprintMapper.toResponse(sprintRepository.save(sprint));

    }

    @Override
    public void assignTask(String sprintId, String taskId){

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new CustomDataNotFoundException("Sprint not found"));

        if (sprint.getStatus() != SprintStatus.PLANNED){

            throw new IllegalStateException("Tasks can only be added to planned sprints");

        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomDataNotFoundException("Task not found"));

        task.setSprint(sprint);

        taskRepository.save(task);

    }
}
