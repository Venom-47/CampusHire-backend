package com.racker.CampusHire.dto.request;

import com.racker.CampusHire.entity.Department;
import com.racker.CampusHire.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterReq {

    private String fullName;
    private String email;
    private String password;
    private Role role;
    private Department department;

    private String uid;
    private Double cgpa;

    private String empId;
}
