package com.gesiath.miniscrumapi.dto;

import com.gesiath.miniscrumapi.enumerator.SprintStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintResponseDTO {

    private String id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private SprintStatus status;

}
