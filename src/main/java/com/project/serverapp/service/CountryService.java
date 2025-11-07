package com.project.serverapp.service;

import com.project.serverapp.model.Country;
import com.project.serverapp.repo.CountryRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CountryService {

  private CountryRepo countryRepo;

  public List<Country> getAll() {
    return countryRepo.findAll();
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

  @SuppressWarnings("null")
  public Country create(Country country) {
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
