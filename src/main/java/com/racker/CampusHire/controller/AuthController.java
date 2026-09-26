package com.racker.CampusHire.controller;

import com.racker.CampusHire.dto.request.LoginReq;
import com.racker.CampusHire.dto.request.RegisterReq;
import com.racker.CampusHire.dto.response.AuthRes;
import com.racker.CampusHire.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthRes register(@Valid @RequestBody RegisterReq req){
        return authService.register(req);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthRes login(@Valid @RequestBody LoginReq req){
        return authService.login(req);
    }
}
