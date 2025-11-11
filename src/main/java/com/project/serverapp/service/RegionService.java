package com.project.serverapp.service;

import com.project.serverapp.model.Region;
import com.project.serverapp.repo.RegionRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RegionService {

  private RegionRepo regionRepo;

  public List<Region> getAll() {
    return regionRepo.findAll();
  }

  @SuppressWarnings("null")
  public Region getById(Integer id) {
    return regionRepo
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!!!")
      );
  }

  public Region create(Region region) {
    if (!regionRepo.searchByName(region.getName()).isEmpty()) {
      throw new ResponseStatusException(
        HttpStatus.CONFLICT,
        "Region is already exists!!!"
      );
    }

    return regionRepo.save(region);
  }

  public Region update(Integer id, Region region) {
    getById(id); // validasi
    region.setId(id); // set id
    return regionRepo.save(region);
  }

  @SuppressWarnings("null")
  public Region delete(Integer id) {
    Region region = getById(id);
    regionRepo.delete(region);
    return region;
  }

  public List<Region> searchByNameNative(String name) {
    String fullName = "%" + name + "%";
    return regionRepo.searchByNameNative(fullName);
  }

  public List<Region> searchByNameJPQL(String name) {
    String fullName = "%" + name + "%";
    return regionRepo.searchByNameJPQL(fullName);
  }
}
