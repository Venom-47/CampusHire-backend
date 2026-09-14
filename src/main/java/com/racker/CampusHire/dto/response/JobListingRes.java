package com.racker.CampusHire.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListingRes {

    private Long id;
    private String companyName;
    private String roleTitle;
    private String jobType;
    private Double salary;
    private String duration;
    private Double minCgpa;
    private LocalDate deadline;
    private String status;
    private String location;
    private String description;
    private LocalDateTime createdAt;
}
