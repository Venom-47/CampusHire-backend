package com.racker.CampusHire.service.impl;

import com.racker.CampusHire.dto.request.JobListingReq;
import com.racker.CampusHire.dto.response.JobListingRes;
import com.racker.CampusHire.entity.JobListing;
import com.racker.CampusHire.entity.User;
import com.racker.CampusHire.exception.ResourceNotFoundException;
import com.racker.CampusHire.repository.JobListingRepo;
import com.racker.CampusHire.repository.UserRepo;
import com.racker.CampusHire.service.JobListingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobListingServiceImpl implements JobListingService {

    private final JobListingRepo jobListingRepo;
    private final UserRepo userRepo;

    @Override
    public JobListingRes createJob(JobListingReq req, String tpoEmail) {

        log.info("Creating new job drive for company: {} by tpo: {}", req.getCompanyName(), tpoEmail);

        User tpo = userRepo.findByEmail(tpoEmail)
                .orElseThrow(() -> new ResourceNotFoundException("TPO not found with email: " + tpoEmail));

        JobListing jobListing = JobListing.builder()
                .companyName(req.getCompanyName())
                .roleTitle(req.getRoleTitle())
                .jobType(req.getJobType())
                .salary(req.getSalary())
                .duration(req.getDuration())
                .minCgpa(req.getMinCgpa())
                .deadline(req.getDeadline())
                .location(req.getLocation())
                .description(req.getDescription())
                .status("OPEN")
                .postedBy(tpo)
                .createdAt(LocalDateTime.now())
                .build();

        JobListing savedJob = jobListingRepo.save(jobListing);
        log.info("Successfully created job listing with Id: {}", savedJob.getId());

        return mapToResponseDto(savedJob);
    }

    @Override
    public List<JobListingRes> getAllOpenJobs() {
        log.info("Fetching all open job drives");
        return jobListingRepo
                .findByStatusAndDeadlineGreaterThanEqual("OPEN", LocalDate.now())
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<JobListingRes> getAllJobs() {
        log.info("Fetching all job drives (Admin view)");
        return jobListingRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public JobListingRes getJobById(Long id) {
        log.info("Fetching job details for Id: {}", id);

        JobListing jobListing = jobListingRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Job listing not found with id: " + id));

        return mapToResponseDto(jobListing);
    }

    @Override
    public JobListingRes toggleJobStatus(Long id, String status) {

        log.info("Updating status for id: {} to {}", id, status);

        JobListing job = jobListingRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Job listing not found with id: " + id));

        job.setStatus(status.toUpperCase());
        JobListing updatedJob = jobListingRepo.save(job);

        return mapToResponseDto(updatedJob);
    }

    private JobListingRes mapToResponseDto(JobListing entity) {

        String postedByName = "TPO Cell";
        String postedByEmail = null;

        if (entity.getPostedBy() != null) {
            if(entity.getPostedBy().getFullName() != null) {
                postedByName = entity.getPostedBy().getFullName();
            }
            postedByEmail = entity.getPostedBy().getEmail();
        }

        return JobListingRes.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .roleTitle(entity.getRoleTitle())
                .jobType(entity.getJobType())
                .salary(entity.getSalary())
                .duration(entity.getDuration())
                .minCgpa(entity.getMinCgpa())
                .deadline(entity.getDeadline())
                .location(entity.getLocation())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .postedByName(postedByName)
                .postedByEmail(postedByEmail)
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
