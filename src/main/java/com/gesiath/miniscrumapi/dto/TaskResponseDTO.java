package com.gesiath.miniscrumapi.dto;

import com.gesiath.miniscrumapi.enumerator.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {

    private String id;
    private String title;
    private String description;
    private Status status;
    private LocalDate doDate;
    private LocalDate createdAt;
    private String userName;
    private String sprintId;
    private String sprintName;

}
