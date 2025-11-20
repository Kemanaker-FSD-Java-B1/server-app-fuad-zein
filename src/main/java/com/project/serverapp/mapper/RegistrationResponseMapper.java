package com.project.serverapp.mapper;

import com.project.serverapp.dto.response.RegistrationResponse;
import com.project.serverapp.model.Role;
import com.project.serverapp.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationResponseMapper {
  
  @Mapping(source = "employee.name", target = "name")
  @Mapping(source = "employee.email", target = "email")
  @Mapping(source = "employee.phone", target = "phone")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "roles", target = "roles")
  RegistrationResponse toRegistrationResponse(User user);

  default List<String> mapRole(List<Role> roles) {
    return roles.stream().map(Role::getName).toList();
  }
}
