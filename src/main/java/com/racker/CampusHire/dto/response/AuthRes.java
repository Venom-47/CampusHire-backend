package com.racker.CampusHire.dto.response;

import com.racker.CampusHire.entity.Department;
import com.racker.CampusHire.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRes {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
    private String email;

    private String fullName;
    private Role role;
    private Department department;

    private String uid;
    private Double cgpa;

    private String empId;
}
