package com.study.security.service;

import com.study.security.dto.SignInResultDto;
import com.study.security.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(String id, String password, String name, String role);

    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
