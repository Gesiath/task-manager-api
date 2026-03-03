package com.gesiath.miniscrumapi.service;

import com.gesiath.miniscrumapi.dto.CreateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.TaskResponseDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskStatusRequestDTO;
import com.gesiath.miniscrumapi.entity.Task;
import com.gesiath.miniscrumapi.entity.User;
import com.gesiath.miniscrumapi.enumerator.Status;
import com.gesiath.miniscrumapi.exception.CustomDataNotFoundException;
import com.gesiath.miniscrumapi.mapper.TaskMapper;
import com.gesiath.miniscrumapi.respository.TaskRepository;
import com.gesiath.miniscrumapi.respository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskService implements ITaskService{


    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<TaskResponseDTO> getAll(Pageable pageable) {

        return taskRepository.findAll(pageable)
                .map(TaskMapper::toResponse);
    }

    @Override
    public TaskResponseDTO getById(String id){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomDataNotFoundException("Task not found"));

        return TaskMapper.toResponse(task);

    }

    @Override
    public Page<TaskResponseDTO> getByUser_Id(String user_Id, Pageable pageable){

        if (!userRepository.existsById(user_Id)){
            throw new CustomDataNotFoundException("User not found");
        }

        return taskRepository.findByUser_Id(user_Id, pageable)
                .map(TaskMapper::toResponse);
    }

    @Override
    public Page<TaskResponseDTO> getByUser_IdAndStatus(String user_Id, Status status, Pageable pageable){

        if (!userRepository.existsById(user_Id)){
            throw new CustomDataNotFoundException("User not found");
        }

        return taskRepository.findByUser_IdAndStatus(user_Id, status, pageable)
                .map(TaskMapper::toResponse);

    }

    @Override
    public Page<TaskResponseDTO> getByStatus(Status status, Pageable pageable){

        return taskRepository.findByStatus(status, pageable)
                .map(TaskMapper::toResponse);

    }

    @Override
    public TaskResponseDTO create(CreateTaskRequestDTO dto) {

        User user = null;

        if (dto.getUserId() != null){

            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new CustomDataNotFoundException("User not found"));

        }

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .doDate(dto.getDoDate())
                .user(user)
                .build();

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO update(String id, UpdateTaskRequestDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomDataNotFoundException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        validateStatusTransition(task.getStatus(), dto.getStatus());
        task.setStatus(dto.getStatus());
        task.setDoDate(dto.getDoDate());

        if (dto.getUserId() != null){

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new CustomDataNotFoundException("User not found"));
            task.setUser(user);

        } else {

            task.setUser(null);

        }

        return TaskMapper.toResponse(taskRepository.save(task));

    }

    @Override
    public TaskResponseDTO patchStatus(String id, UpdateTaskStatusRequestDTO dto){

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomDataNotFoundException("Task not found"));

        validateStatusTransition(task.getStatus(), dto.getStatus());

        task.setStatus(dto.getStatus());

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public void delete(String id) {

        if (!taskRepository.existsById(id)) {
            throw new CustomDataNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);

    }

    private void validateStatusTransition(Status current, Status next){

        if (current == Status.DONE){
            throw new IllegalStateException("Cannot modify a DONE task");
        }

        if (current == Status.TODO && next == Status.IN_PROGRESS){
            throw new IllegalStateException("Task must be IN_PLANNING before IN_PROGRESS");
        }

        if (current == Status.TODO && next == Status.DONE){
            throw new IllegalStateException("Task must be IN_PROGRESS before DONE");
        }

        if (current == next){
            return;
        }

        if (current == Status.IN_PROGRESS && next == Status.TODO){
            throw new IllegalStateException("Cannot move task back to TODO");
        }

        if (current == Status.IN_PROGRESS && next == Status.IN_PLANNING){
            throw new IllegalStateException("Cannot move task back to IN_PLANNING");
        }

        if (current == Status.IN_PLANNING && next == Status.TODO){
            throw new IllegalStateException("Cannot move task back to TODO");
        }

        if (current == Status.IN_PLANNING && next == Status.DONE){
            throw new IllegalStateException("Task must be IN_PROGRESS before DONE");
        }

    }
}
