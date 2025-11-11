package com.project.serverapp.repo;

import com.project.serverapp.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {
  // Native
  @Query(
    value = "SELECT * FROM db_region WHERE region_name LIKE ?1",
    nativeQuery = true
  )
  List<Region> searchByNameNative(String name);

  // JPQL
  @Query("SELECT r FROM Region r WHERE r.name LIKE :name")
  List<Region> searchByNameJPQL(String name);

  // Query Method
  List<Region> searchByName(String name);
}
/**
 * Parameterized Query:
 * 1. Base position => ?1, ?2, ?3
 * 2. Base name     => :name, :age
 */
