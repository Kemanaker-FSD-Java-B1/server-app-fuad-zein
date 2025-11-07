package com.project.serverapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {

  private Integer countryId;
  private String countryCode;
  private String countryName;
  private Integer regionId;
  private String regionName;
}
