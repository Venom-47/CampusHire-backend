package com.racker.CampusHire.service.impl;

import com.racker.CampusHire.dto.request.ApplicationReq;
import com.racker.CampusHire.dto.request.StatusUpdateReq;
import com.racker.CampusHire.dto.response.ApplicationRes;
import com.racker.CampusHire.dto.response.JobListingRes;
import com.racker.CampusHire.entity.Application;
import com.racker.CampusHire.entity.ApplicationStatus;
import com.racker.CampusHire.entity.JobListing;
import com.racker.CampusHire.exception.DeadlinePassedException;
import com.racker.CampusHire.exception.DuplicateApplicationException;
import com.racker.CampusHire.exception.IneligibleStudentException;
import com.racker.CampusHire.exception.ResourceNotFoundException;
import com.racker.CampusHire.repository.ApplicationRepo;
import com.racker.CampusHire.repository.JobListingRepo;
import com.racker.CampusHire.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final JobListingRepo jobListingRepo;

    @Override
    public ApplicationRes applyForJob(ApplicationReq req) {

        log.info("Student {} ({}) applying for Job id: {}",req.getStudentName(),req.getStudentEmail(),req.getJobId());

        JobListing job = jobListingRepo.findById(req.getJobId())
            .orElseThrow(() -> new ResourceNotFoundException("Job listing not found with id: " + req.getJobId()));

        if(!"OPEN".equalsIgnoreCase(job.getStatus())) {
            log.warn("Application rejected: Job ID {} is closed", req.getJobId());

            throw new DeadlinePassedException("This job drive is currently closed.");
        }

        if(LocalDate.now().isAfter(job.getDeadline())) {
            log.warn("Application rejected: Deadline passed for Job ID {}", req.getJobId());

            throw new DeadlinePassedException("The deadline for this job was: " + job.getDeadline());
        }

        if(applicationRepo.existsByJobListingIdAndStudentEmail(req.getJobId(), req.getStudentEmail())) {
            log.warn("Application rejected: Duplicate application by {} for job id: {}", req.getStudentName(), req.getJobId());

            throw new DuplicateApplicationException("You have already applied for this job.");
        }

        if(req.getCgpa() < job.getMinCgpa()){
            log.warn("Application rejected: Ineligible CGPA {} for required {}",req.getCgpa(),job.getMinCgpa());
            throw new IneligibleStudentException(String.format(
                "Your CGPA (%.2f) does not meet the minimum required CGPA (%.2f) for %s.",
                req.getCgpa(),job.getMinCgpa(),job.getCompanyName()
            ));
        }

        Application application = Application.builder()
            .jobListing(job)
            .studentName(req.getStudentName())
            .studentEmail(req.getStudentEmail())
            .uid(req.getUid())
            .department(req.getDepartment())
            .cgpa(req.getCgpa())
            .status(ApplicationStatus.APPLIED)
            .resumeUrl(req.getResumeUrl())
            .appliedAt(LocalDateTime.now())
            .build();

        Application savedApp = applicationRepo.save(application);

        return mapToResponse(savedApp);
    }

    @Override
    public List<ApplicationRes> getMyApplications(String studentEmail) {
        log.info("Fetching all applications for student {}", studentEmail);
        return applicationRepo.findByStudentEmailIgnoreCase(studentEmail)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<ApplicationRes> getApplicationsByJob(Long jobId) {
        log.info("Fetching applications for Job ID {}", jobId);
        return applicationRepo.findByJobListingId(jobId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public ApplicationRes updateStatus(Long applicationId, StatusUpdateReq req) {
        log.info("Updating application status for application id {} to {}", applicationId, req.getStatus());
        Application application = applicationRepo.findById(applicationId)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        application.setStatus(req.getStatus());
        application.setNotes(req.getNotes());

        Application updatedApp = applicationRepo.save(application);

        return mapToResponse(updatedApp);
    }

    private ApplicationRes mapToResponse(Application application) {
        return ApplicationRes.builder()
            .id(application.getId())
            .jobId(application.getJobListing().getId())
            .companyName(application.getJobListing().getCompanyName())
            .roleTitle(application.getJobListing().getRoleTitle())
            .studentName(application.getStudentName())
            .studentEmail(application.getStudentEmail())
            .uid(application.getUid())
            .department(application.getDepartment())
            .cgpa(application.getCgpa())
            .status(application.getStatus())
            .resumeUrl(application.getResumeUrl())
            .notes(application.getNotes())
            .appliedAt(application.getAppliedAt())
            .build();
    }
}
