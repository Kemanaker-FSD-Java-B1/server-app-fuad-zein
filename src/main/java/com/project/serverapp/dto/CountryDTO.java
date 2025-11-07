package com.project.serverapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

  private Integer id;
  private String code;
  private String name;
  private Integer regionId;
}
