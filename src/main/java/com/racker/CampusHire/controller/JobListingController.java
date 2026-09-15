package com.racker.CampusHire.controller;

import com.racker.CampusHire.dto.request.JobListingReq;
import com.racker.CampusHire.dto.response.JobListingRes;
import com.racker.CampusHire.service.JobListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JobListingController {

    private final JobListingService jobListingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobListingRes createJob(@RequestBody JobListingReq req) {
        return jobListingService.createJob(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobListingRes getJobById(@PathVariable Long id){
        return jobListingService.getJobById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JobListingRes> getOpenJobs(){
        return jobListingService.getAllOpenJobs();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<JobListingRes> getAllJobs(){
        return jobListingService.getAllJobs();
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public JobListingRes updateJobStatus(
            @PathVariable Long id,
            @RequestParam String status){
        return jobListingService.toggleJobStatus(id, status);
    }

}
