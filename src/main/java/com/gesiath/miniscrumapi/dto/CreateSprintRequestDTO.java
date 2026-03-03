package com.gesiath.miniscrumapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSprintRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Start date is required")
    private LocalDate startDate;

    @NotBlank(message = "End date is required")
    private LocalDate endDate;

}
