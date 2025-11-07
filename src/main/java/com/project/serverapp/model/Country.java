package com.project.serverapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "country_id")
  private Integer id;

  @Column(name = "country_code", nullable = false, unique = true, length = 2)
  private String code;

  @Column(name = "country_name", nullable = false, unique = true)
  private String name;

  @ManyToOne
  // @JsonBackReference
  @JoinColumn(name = "region_id", nullable = false)
  private Region region;
}
