package com.gesiath.miniscrumapi.controller;

import com.gesiath.miniscrumapi.dto.CreateSprintRequestDTO;
import com.gesiath.miniscrumapi.dto.SprintResponseDTO;
import com.gesiath.miniscrumapi.service.ISprintService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    private final ISprintService iSprintService;

    public SprintController(ISprintService iSprintService) {
        this.iSprintService = iSprintService;
    }

    @PostMapping
    public ResponseEntity<SprintResponseDTO> create(@Valid @RequestBody CreateSprintRequestDTO dto){

        return ResponseEntity.status(201)
                .body(iSprintService.create(dto));

    }

    @GetMapping
    public ResponseEntity<Page<SprintResponseDTO>> getAll(Pageable pageable){

        return ResponseEntity.ok(iSprintService.getAll(pageable));

    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<SprintResponseDTO> start(@PathVariable String id){

        return ResponseEntity.ok(iSprintService.startSprint(id));

    }

    @PatchMapping("/{id}/close")
    private ResponseEntity<SprintResponseDTO> close(@PathVariable String id){

        return ResponseEntity.ok(iSprintService.closeSprint(id));

    }

    @PatchMapping("/{sprintId}/tasks/{taskId}")
    public ResponseEntity<Void> assignTask(@PathVariable String sprintId,
                                           @PathVariable String taskId){

        iSprintService.assignTask(sprintId,taskId);
        return ResponseEntity.ok().build();

    }

}
