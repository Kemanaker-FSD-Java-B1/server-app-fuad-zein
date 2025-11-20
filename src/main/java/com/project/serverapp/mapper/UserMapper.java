package com.project.serverapp.mapper;

import com.project.serverapp.dto.request.RegistrationRequest;
import com.project.serverapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "employee", ignore = true)
  @Mapping(target = "roles", ignore = true)
  User toUser(RegistrationRequest registrationRequest);
}
