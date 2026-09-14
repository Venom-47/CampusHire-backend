package com.racker.CampusHire.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobListingReq {

    private String companyName;
    private String roleTitle;
    private String jobType;
    private Double salary;
    private String duration;
    private Double minCgpa;
    private LocalDate deadline;
    private String location;
    private String description;
}
