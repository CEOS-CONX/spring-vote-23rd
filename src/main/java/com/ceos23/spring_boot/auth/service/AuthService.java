package com.ceos23.spring_boot.auth.service;

import com.ceos23.spring_boot.auth.dto.LoginRequest;
import com.ceos23.spring_boot.auth.dto.LoginResponse;
import com.ceos23.spring_boot.auth.dto.SignupRequest;
import com.ceos23.spring_boot.auth.dto.SignupResponse;
import com.ceos23.spring_boot.global.exception.CustomException;
import com.ceos23.spring_boot.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public SignupResponse signup(SignupRequest request) {
        if ("duplicate".equals(request.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        return SignupResponse.of(1L, request.getUsername());
    }

    public LoginResponse login(LoginRequest request) {
        if (!"user123".equals(request.getUsername())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_INFO);
        }

        return LoginResponse.of("sample-access-token", "sample-refresh-token");
    }
}