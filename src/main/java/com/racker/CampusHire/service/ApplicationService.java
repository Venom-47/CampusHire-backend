package com.racker.CampusHire.service;

import com.racker.CampusHire.dto.request.ApplicationReq;
import com.racker.CampusHire.dto.request.StatusUpdateReq;
import com.racker.CampusHire.dto.response.ApplicationRes;
import com.racker.CampusHire.entity.Application;
import com.racker.CampusHire.entity.ApplicationStatus;

import java.security.Principal;
import java.util.List;

public interface ApplicationService {

    ApplicationRes applyForJob(ApplicationReq req, String studentEmail);

    List<ApplicationRes> getMyApplications(String studentEmail);

    List<ApplicationRes> getApplicationsByJob(Long jobId);

    ApplicationRes updateStatus(Long applicationId, StatusUpdateReq req);
}
