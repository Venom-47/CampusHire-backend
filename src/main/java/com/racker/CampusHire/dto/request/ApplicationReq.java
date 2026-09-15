package com.racker.CampusHire.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationReq {

    private Long jobId;
    private String studentName;
    private String studentEmail;
    private String uid;
    private String department;
    private double cgpa;
    private String resumeUrl;

}
