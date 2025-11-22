package com.project.serverapp.service;

import com.project.serverapp.dto.request.LoginRequest;
import com.project.serverapp.dto.request.RegistrationRequest;
import com.project.serverapp.dto.response.LoginResponse;
import com.project.serverapp.dto.response.RegistrationResponse;
import com.project.serverapp.mapper.EmployeeMapper;
import com.project.serverapp.mapper.RegistrationResponseMapper;
import com.project.serverapp.mapper.UserMapper;
import com.project.serverapp.model.Employee;
import com.project.serverapp.model.Role;
import com.project.serverapp.model.User;
import com.project.serverapp.repo.UserRepo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private EmployeeMapper employeeMapper;
  private UserMapper userMapper;
  private RoleService roleService;
  private UserRepo userRepo;
  private RegistrationResponseMapper registrationResponseMapper;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private AppUserDetailService appUserDetailService;

  public RegistrationResponse registration(
    RegistrationRequest registrationRequest
  ) {
    // mapping dto -> employee
    Employee employee = employeeMapper.toEmployee(registrationRequest);

    // mapping dto -> user
    User user = userMapper.toUser(registrationRequest);

    // set password
    user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

    // default role
    Role defaultRole = roleService.getById(2);
    user.setRoles(List.of(defaultRole));

    // relasi one to one
    employee.setUser(user);
    user.setEmployee(employee);

    // save
    User saveUser = userRepo.save(user);

    // response
    return registrationResponseMapper.toRegistrationResponse(saveUser);
  }

  public LoginResponse login(LoginRequest loginRequest) {
    // get username & password
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
      loginRequest.getUsername(),
      loginRequest.getPassword()
    );

    // set session
    Authentication auth = authenticationManager.authenticate(authReq);
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(auth);

    // get username
    UserDetails userDetails = appUserDetailService.loadUserByUsername(
      loginRequest.getUsername()
    );

    // get email
    User user = userRepo
      .findByUsernameOrEmployeeEmail(
        loginRequest.getUsername(),
        loginRequest.getUsername()
      )
      .get();

    // get authorities
    List<String> authorities = userDetails
      .getAuthorities()
      .stream()
      .map(authority -> authority.getAuthority())
      .collect(Collectors.toList());

    // return response
    LoginResponse loginResponse = LoginResponse
      .builder()
      .username(userDetails.getUsername())
      .email(user.getEmployee().getEmail())
      .authorities(authorities)
      .build();

    return loginResponse;
  }
}
