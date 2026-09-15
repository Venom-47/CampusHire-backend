package com.racker.CampusHire.repository;

import com.racker.CampusHire.entity.Application;
import com.racker.CampusHire.entity.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

    boolean existsByJobListingIdAndStudentEmail(Long jobId, String studentEmail);

    List<Application> findByJobListingId(Long jobListingId);

    List<Application> findByStudentEmailIgnoreCase(String studentEmail);

    List<Application> findByStatus(Application status);
}
