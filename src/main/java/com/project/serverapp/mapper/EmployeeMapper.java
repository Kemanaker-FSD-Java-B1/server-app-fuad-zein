package com.project.serverapp.mapper;

import com.project.serverapp.dto.request.RegistrationRequest;
import com.project.serverapp.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
  
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  Employee toEmployee(RegistrationRequest registrationRequest);
}
