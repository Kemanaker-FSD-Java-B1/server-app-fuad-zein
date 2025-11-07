package com.project.serverapp.controller;

import com.project.serverapp.dto.CountryDTO;
import com.project.serverapp.dto.request.CountryRequest;
import com.project.serverapp.dto.response.CountryResponse;
import com.project.serverapp.model.Country;
import com.project.serverapp.service.CountryService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/country")
public class CountryController {

  private CountryService countryService;

  @GetMapping
  public List<Country> getAll() {
    return countryService.getAll();
  }

  // get all with map
  @GetMapping("/all-map")
  public List<Map<String, Object>> getAllMap() {
    return countryService.getAllMap();
  }

  // get all with build
  @GetMapping("/all-build")
  public List<CountryResponse> getAllBuild() {
    return countryService.getAllBuild();
  }

  @GetMapping("/dto-mapstruct")
  public List<CountryDTO> getAllDTOMapstruct() {
    return countryService.getAllDTOMapstruct();
  }

  @GetMapping("/{id}")
  public Country getById(@PathVariable Integer id) {
    return countryService.getById(id);
  }

  // without dto
  @PostMapping
  public Country create(@RequestBody Country country) {
    return countryService.create(country);
  }

  // with manual dto
  @PostMapping("/dto-manual")
  public Country createDTOManual(@RequestBody CountryRequest countryRequest) {
    return countryService.createDTOManual(countryRequest);
  }

  // with dto bean utils
  @PostMapping("/dto-bean")
  public Country createDTOBean(@RequestBody CountryRequest countryRequest) {
    return countryService.createDTOBean(countryRequest);
  }

  // with dto mapstruct
  @PostMapping("/dto-mapstruct")
  public Country createDTOMapstruct(@RequestBody CountryDTO countryDTO) {
    return countryService.createDTOMapstruct(countryDTO);
  }

  // with dto model mapper
  @PostMapping("/dto-model")
  public Country createDTOModelMapper(
    @RequestBody CountryRequest countryRequest
  ) {
    return countryService.createDTOModelMapper(countryRequest);
  }

  @PutMapping("/{id}")
  public Country update(
    @PathVariable Integer id,
    @RequestBody Country country
  ) {
    return countryService.update(id, country);
  }

  @DeleteMapping("/{id}")
  public Country delete(@PathVariable Integer id) {
    return countryService.delete(id);
  }
}
