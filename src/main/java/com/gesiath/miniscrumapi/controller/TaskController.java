package com.gesiath.miniscrumapi.controller;

import com.gesiath.miniscrumapi.dto.CreateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.TaskResponseDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskRequestDTO;
import com.gesiath.miniscrumapi.dto.UpdateTaskStatusRequestDTO;
import com.gesiath.miniscrumapi.enumerator.Status;
import com.gesiath.miniscrumapi.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ITaskService iTaskService;

    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAll(@RequestParam(required = false) String user_Id,
                                                        @RequestParam(required = false) Status status,
                                                        Pageable pageable){

        if (user_Id != null && status != null) {
            return ResponseEntity.ok(iTaskService.getByUser_IdAndStatus(user_Id, status, pageable));
        }

        if (user_Id != null){

            return ResponseEntity.ok(iTaskService.getByUser_Id(user_Id, pageable));

        }

        if (status != null){

            return ResponseEntity.ok(iTaskService.getByStatus(status, pageable));

        }

        return ResponseEntity.ok(iTaskService.getAll(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable String id){

        return ResponseEntity.ok(iTaskService.getById(id));

    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@Valid @RequestBody CreateTaskRequestDTO dto){

        return ResponseEntity.status(201).body(iTaskService.create(dto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@Valid @PathVariable String id,
                                                  @RequestBody UpdateTaskRequestDTO dto){

        return ResponseEntity.ok(iTaskService.update(id, dto));

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> patchStatus(@PathVariable String id,
                                                       @Valid @RequestBody UpdateTaskStatusRequestDTO dto){

        return ResponseEntity.ok(iTaskService.patchStatus(id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){

        iTaskService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
