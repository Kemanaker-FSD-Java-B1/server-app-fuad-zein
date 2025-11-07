package com.project.serverapp.service;

import com.project.serverapp.dto.CountryDTO;
import com.project.serverapp.dto.request.CountryRequest;
import com.project.serverapp.mapper.CountryMapper;
import com.project.serverapp.model.Country;
import com.project.serverapp.model.Region;
import com.project.serverapp.repo.CountryRepo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
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

  public List<Country> getAll() {
    return countryRepo.findAll();
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
