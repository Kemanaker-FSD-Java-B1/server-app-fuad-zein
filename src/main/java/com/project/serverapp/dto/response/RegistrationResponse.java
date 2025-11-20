package com.project.serverapp.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

  private String name;
  private String email;
  private String phone;
  private String username;
  private List<String> roles;
}
