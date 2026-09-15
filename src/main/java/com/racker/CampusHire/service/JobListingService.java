package com.racker.CampusHire.service;

import com.racker.CampusHire.dto.request.JobListingReq;
import com.racker.CampusHire.dto.response.JobListingRes;

import java.util.List;

public interface JobListingService {

    JobListingRes createJob(JobListingReq req);

    List<JobListingRes> getAllOpenJobs();

    List<JobListingRes> getAllJobs();

    JobListingRes getJobById(Long id);

    JobListingRes toggleJobStatus(Long id, String status);

}
