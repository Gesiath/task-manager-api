package com.gesiath.miniscrumapi.dto;

import com.gesiath.miniscrumapi.entity.Task;
import com.gesiath.miniscrumapi.enumerator.SprintStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSprintRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private SprintStatus status;

    private List<Task> tasks;

}
