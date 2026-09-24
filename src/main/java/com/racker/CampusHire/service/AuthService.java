package com.racker.CampusHire.service;

import com.racker.CampusHire.dto.request.LoginReq;
import com.racker.CampusHire.dto.request.RegisterReq;
import com.racker.CampusHire.dto.response.AuthRes;

public interface AuthService {

    AuthRes register(RegisterReq req);
    AuthRes login(LoginReq req);
}
