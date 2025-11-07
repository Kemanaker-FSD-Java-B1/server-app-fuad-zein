package com.project.serverapp.mapper;

import com.project.serverapp.dto.CountryDTO;
import com.project.serverapp.model.Country;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
  @Mapping(source = "region.id", target = "regionId")
  CountryDTO toCountryDTO(Country country);

  @Mapping(source = "regionId", target = "region.id")
  Country toCountry(CountryDTO countryDTO);

  List<CountryDTO> todoList(List<Country> countries);
}
