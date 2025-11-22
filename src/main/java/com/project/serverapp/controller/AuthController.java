package com.project.serverapp.controller;

import com.project.serverapp.dto.request.LoginRequest;
import com.project.serverapp.dto.request.RegistrationRequest;
import com.project.serverapp.dto.response.LoginResponse;
import com.project.serverapp.dto.response.RegistrationResponse;
import com.project.serverapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;

  @PostMapping("/registration")
  public RegistrationResponse registration(
    @RequestBody RegistrationRequest registrationRequest
  ) {
    return authService.registration(registrationRequest);
  }

  @PostMapping("/login")
  public LoginResponse login(
    @RequestBody LoginRequest loginRequest,
    HttpServletRequest request
  ) {
    LoginResponse loginResponse = authService.login(loginRequest);

    // ? memastikan SecurityContext tersimpan di session setelah di-set di AuthService
    HttpSession session = request.getSession(true); //? membuat session jika belum ada
    session.setAttribute(
      HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
      SecurityContextHolder.getContext()
    );

    return loginResponse;
  }
}
