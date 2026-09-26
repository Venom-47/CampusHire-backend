package com.racker.CampusHire.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListingReq {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Role title is required")
    private String roleTitle;

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be a positive number")
    private Double salary;

    private String duration;

    @NotNull(message = "Minimum CGPA requirement is required")
    @DecimalMin(value = "0.0", message = "Minimum CGPA cannot be negative")
    @DecimalMax(value = "10.0", message = "Minimum CGPA cannot exceed 10.0")
    private Double minCgpa;

    @NotNull(message = "Application deadline is required")
    @Future(message = "Deadline must be a future date")
    private LocalDate deadline;

    private String location;
    private String description;
}
