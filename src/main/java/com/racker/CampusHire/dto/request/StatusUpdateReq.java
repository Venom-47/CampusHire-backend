package com.racker.CampusHire.dto.request;

import com.racker.CampusHire.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusUpdateReq {

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    private String notes;
}
