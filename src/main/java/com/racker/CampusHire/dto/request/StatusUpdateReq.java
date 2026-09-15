package com.racker.CampusHire.dto.request;

import com.racker.CampusHire.entity.ApplicationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusUpdateReq {
    private ApplicationStatus status;
    private String notes;
}
