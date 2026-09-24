package com.racker.CampusHire.service.impl;

import com.racker.CampusHire.dto.request.LoginReq;
import com.racker.CampusHire.dto.request.RegisterReq;
import com.racker.CampusHire.dto.response.AuthRes;
import com.racker.CampusHire.entity.Role;
import com.racker.CampusHire.entity.User;
import com.racker.CampusHire.exception.ResourceNotFoundException;
import com.racker.CampusHire.repository.UserRepo;
import com.racker.CampusHire.security.JwtService;
import com.racker.CampusHire.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthRes register(RegisterReq req) {

        log.info("Attempting registration for email: {}", req.getEmail());

        if(userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email is already registered:  " + req.getEmail());
        }

        if(req.getRole() == Role.ROLE_STUDENT && req.getUid() != null && userRepo.existsByUid(req.getUid())) {
            throw new  IllegalArgumentException("Student uid is already registered:  " + req.getUid());
        }

        if(req.getRole() == Role.ROLE_TPO_ADMIN && req.getEmpId() != null && userRepo.existsByUid(req.getEmpId())) {
            throw new  IllegalArgumentException("Employee uid is already registered:  " + req.getEmpId());
        }

        User user = User.builder()
            .fullName(req.getFullName())
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .role(req.getRole())
            .department(req.getDepartment())
            .uid(req.getUid())
            .cgpa(req.getCgpa())
            .empId(req.getEmpId())
            .build();

        User savedUser = userRepo.save(user);
        log.info("Successfully registered user id: {} with role: {}", savedUser.getId(), savedUser.getRole());

        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("role", savedUser.getRole());
        extraClaims.put("fullName", savedUser.getFullName());

        String jwtToken = jwtService.generateToken(extraClaims, savedUser);

        return mapToAuthRes(savedUser,jwtToken);
    }

    private AuthRes mapToAuthRes(User user, String jwtToken) {

        return AuthRes.builder()
            .token(jwtToken)
            .tokenType("Bearer")
            .email(user.getEmail())
            .fullName(user.getFullName())
            .role(user.getRole())
            .department(user.getDepartment())
            .uid(user.getUid())
            .cgpa(user.getCgpa())
            .empId(user.getEmpId())
            .build();
    }

    @Override
    public AuthRes login(LoginReq req) {

        log.info("Attempting login for email: {}", req.getEmail());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepo.findByEmail(req.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + req.getEmail()));

        log.info("User {} successfully authenticated with role: {}", user.getEmail(), user.getRole());

        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("fullName", user.getFullName());

        String jwtToken = jwtService.generateToken(extraClaims, user);
        return mapToAuthRes(user, jwtToken);
    }
}
