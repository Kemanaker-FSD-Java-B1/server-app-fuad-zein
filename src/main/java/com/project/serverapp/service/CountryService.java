package com.project.serverapp.service;

import com.project.serverapp.dto.CountryDTO;
import com.project.serverapp.dto.request.CountryRequest;
import com.project.serverapp.dto.response.CountryResponse;
import com.project.serverapp.mapper.CountryMapper;
import com.project.serverapp.model.Country;
import com.project.serverapp.model.Region;
import com.project.serverapp.repo.CountryRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CountryService {

  private CountryRepo countryRepo;
  private RegionService regionService;
  private CountryMapper countryMapper;
  private ModelMapper modelMapper;

  public List<Country> getAll() {
    return countryRepo.findAll();
  }

  // get all with map
  public List<Map<String, Object>> getAllMap() {
    return countryRepo
      .findAll()
      .stream()
      .map(country -> {
        Map<String, Object> result = new HashMap<>();

        result.put("Country id", country.getId());
        result.put("Country code", country.getCode());
        result.put("Country name", country.getName());
        result.put("Region id", country.getRegion().getId());
        result.put("Region name", country.getRegion().getName());

        return result;
      })
      .collect(Collectors.toList());
  }

  // get all with build
  public List<CountryResponse> getAllBuild() {
    return countryRepo
      .findAll()
      .stream()
      .map(country -> {
        return CountryResponse
          .builder()
          .countryId(country.getId())
          .countryCode(country.getCode())
          .countryName(country.getName())
          .regionId(country.getRegion().getId())
          .regionName(country.getRegion().getName())
          .build();
      })
      .collect(Collectors.toList());
  }

  public List<CountryDTO> getAllDTOMapstruct() {
    List<CountryDTO> results = countryMapper.todoList(getAll());
    return results;
  }

  @SuppressWarnings("null")
  public Country getById(Integer id) {
    return countryRepo
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Country not found!!!"
        )
      );
  }

  // without dto
  @SuppressWarnings("null")
  public Country create(Country country) {
    return countryRepo.save(country);
  }

  // with manual dto
  public Country createDTOManual(CountryRequest countryRequest) {
    Country country = new Country();
    country.setCode(countryRequest.getCode());
    country.setName(countryRequest.getName());

    Region region = regionService.getById(countryRequest.getRegionId());
    country.setRegion(region);

    return countryRepo.save(country);
  }

  // with dto bean utils
  @SuppressWarnings("null")
  public Country createDTOBean(CountryRequest countryRequest) {
    Country country = new Country();
    BeanUtils.copyProperties(countryRequest, country);

    Region region = regionService.getById(countryRequest.getRegionId());
    country.setRegion(region);

    return countryRepo.save(country);
  }

  // with dto mapstruct
  public Country createDTOMapstruct(CountryDTO countryDTO) {
    Country country = countryMapper.toCountry(countryDTO);

    Region region = regionService.getById(countryDTO.getRegionId());
    country.setRegion(region);

    return countryRepo.save(country);
  }

  // with dto model mapper
  public Country createDTOModelMapper(CountryRequest countryRequest) {
    Country country = modelMapper.map(countryRequest, Country.class);

    Region region = regionService.getById(countryRequest.getRegionId());
    country.setRegion(region);

    return countryRepo.save(country);
  }

  public Country update(Integer id, Country country) {
    getById(id); // validasi
    country.setId(id); // set id
    return countryRepo.save(country);
  }

  @SuppressWarnings("null")
  public Country delete(Integer id) {
    Country country = getById(id);
    countryRepo.delete(country);
    return country;
  }
}
