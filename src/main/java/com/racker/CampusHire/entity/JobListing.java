package com.racker.CampusHire.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_listings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String roleTitle;

    @Column(nullable = false)
    private String jobType;

    private Double salary;

    private String duration;

    @Column(nullable = false)
    private Double minCgpa;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    @Builder.Default
    private String status = "OPEN";

    private String location;

    private String description;

    private LocalDateTime createdAt;
}
