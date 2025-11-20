package com.project.serverapp.service;

import com.project.serverapp.dto.request.RegistrationRequest;
import com.project.serverapp.dto.response.RegistrationResponse;
import com.project.serverapp.mapper.EmployeeMapper;
import com.project.serverapp.mapper.RegistrationResponseMapper;
import com.project.serverapp.mapper.UserMapper;
import com.project.serverapp.model.Employee;
import com.project.serverapp.model.Role;
import com.project.serverapp.model.User;
import com.project.serverapp.repo.UserRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private EmployeeMapper employeeMapper;
  private UserMapper userMapper;
  private RoleService roleService;
  private UserRepo userRepo;
  private RegistrationResponseMapper registrationResponseMapper;

  public RegistrationResponse registration(
    RegistrationRequest registrationRequest
  ) {
    // mapping dto -> employee
    Employee employee = employeeMapper.toEmployee(registrationRequest);

    // mapping dto -> user
    User user = userMapper.toUser(registrationRequest);

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
}
