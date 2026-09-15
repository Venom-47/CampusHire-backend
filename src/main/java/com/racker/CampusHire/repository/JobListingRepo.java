package com.racker.CampusHire.repository;

import com.racker.CampusHire.entity.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface JobListingRepo extends JpaRepository<JobListing, Long> {

    List<JobListing> findByStatus(String status);

    List<JobListing> findByStatusAndDeadlineGreaterThanEqual(String status, LocalDate currentDate);

    List<JobListing> findByCompanyNameContainingIgnoreCase(String companyName);
}
