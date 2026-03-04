package com.gesiath.miniscrumapi.service;

import com.gesiath.miniscrumapi.entity.Sprint;
import com.gesiath.miniscrumapi.entity.Task;
import com.gesiath.miniscrumapi.enumerator.SprintStatus;
import com.gesiath.miniscrumapi.enumerator.Status;
import com.gesiath.miniscrumapi.respository.SprintRepository;
import com.gesiath.miniscrumapi.respository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SprintServiceTest {

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private SprintService sprintService;

    @Test
    void shouldNotAllowTwoActiveSprints(){

        Sprint sprint = Sprint.builder()
                .id("1")
                .status(SprintStatus.PLANNED)
                .build();

        when(sprintRepository.findById("1"))
                .thenReturn(Optional.of(sprint));

        when(sprintRepository.existsByStatus(SprintStatus.ACTIVE))
                .thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> sprintService.startSprint("1"));


    }

    @Test
    void shouldNotClosePlannedSprint(){

        Sprint sprint = Sprint.builder()
                .id("1")
                .status(SprintStatus.PLANNED)
                .build();

        when(sprintRepository.findById("1"))
                .thenReturn(Optional.of(sprint));

        assertThrows(IllegalStateException.class,
                () -> sprintService.closeSprint("1"));

    }

    @Test
    void shouldMoveUnfinishedTasksToBacklogWhenClosingSprint(){

        Task doneTask = Task.builder()
                .status(Status.DONE)
                .build();

        Task pendingTask = Task.builder()
                .status(Status.TODO)
                .build();

        Sprint sprint = Sprint.builder()
                .id("1")
                .status(SprintStatus.ACTIVE)
                .tasks(List.of(doneTask, pendingTask))
                .build();

        doneTask.setSprint(sprint);
        pendingTask.setSprint(sprint);

        when(sprintRepository.findById("1"))
                .thenReturn(Optional.of(sprint));

        when(sprintRepository.save(sprint))
                .thenReturn(sprint);

        sprintService.closeSprint("1");

        assertNull(pendingTask.getSprint());
    }

}
