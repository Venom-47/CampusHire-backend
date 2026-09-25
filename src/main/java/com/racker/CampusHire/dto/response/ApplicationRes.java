package com.racker.CampusHire.dto.response;

import com.racker.CampusHire.entity.ApplicationStatus;
import com.racker.CampusHire.entity.Department;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRes {

    private Long id;

    private Long jobId;
    private String companyName;
    private String roleTitle;

    private String studentName;
    private String studentEmail;
    private String uid;
    private Department department;
    private double cgpa;

    private ApplicationStatus status;
    private String resumeUrl;
    private String notes;
    private LocalDateTime appliedAt;
}
