package com.project.serverapp.repo;

import com.project.serverapp.model.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<Country, Integer> {
  List<Country> findByNameOrRegion_Name(String name, String regionName);
}
