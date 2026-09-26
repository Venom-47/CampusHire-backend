package com.racker.CampusHire.controller;

import com.racker.CampusHire.dto.request.ApplicationReq;
import com.racker.CampusHire.dto.request.StatusUpdateReq;
import com.racker.CampusHire.dto.response.ApplicationRes;
import com.racker.CampusHire.entity.ApplicationStatus;
import com.racker.CampusHire.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationRes applyForJob(@Valid @RequestBody ApplicationReq applicationReq, Principal principal) {
        return applicationService.applyForJob(applicationReq, principal.getName());
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationRes> getMyApplications(Principal principal) {
        return applicationService.getMyApplications(principal.getName());
    }

    @GetMapping("/job/{jobId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationRes> getJobApplications(@PathVariable Long jobId) {
        return applicationService.getApplicationsByJob(jobId);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationRes updateStatus(
        @PathVariable Long id,
        @Valid@RequestBody StatusUpdateReq statusUpdateReq) {
        return  applicationService.updateStatus(id, statusUpdateReq);
    }
}
