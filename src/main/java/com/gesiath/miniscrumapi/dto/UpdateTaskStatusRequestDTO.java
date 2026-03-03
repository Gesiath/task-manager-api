package com.gesiath.miniscrumapi.dto;

import com.gesiath.miniscrumapi.enumerator.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskStatusRequestDTO {

    @NotNull(message = "Status is required")
    private Status status;

}
